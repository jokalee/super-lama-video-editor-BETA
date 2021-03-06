/* 
 * Copyright 2016 nathan.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package start;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import tools.ItemOption;
import tools.Outline;
import tools.SourceWindow;
import tools.TimeLine;
import API.SlveFrame;
import API.SlveMenuItem;

public class Start {

	static MainWindow mainWindow;
	static TimeLine timeline;
	static Outline outline;
	static ItemOption itemoptions;
	static SourceWindow srcWindow;
	
	static ArrayList<SlveMenuItem> baritems = new ArrayList<>();
	static JMenuBar jmenubar;
	static ArrayListWithName<Object> tree;
	static ArrayList<SlveFrame> frames = new ArrayList<>();
        static boolean debug = false;
        
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
                if (args.length > 0 && (args[0].equals ("--debug") || args[0].equals ("-d"))){
                    debug = true;
                }
                
		try {
			String slvePath = new File (new File (new MainWindow().getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParent()).getAbsolutePath() + File.separator;
			if(debug) System.out.println("slve location : " + slvePath);
                        if ( ! new File (slvePath + "slve.init").exists() 			//
					|| new File (slvePath + "initme").exists() 			//
					|| new File (slvePath + "initme.txt").exists()) 	//
				new inittools.MainWindow(slvePath);
			else {
				System.out.println("hello world");
				mainWindow = new MainWindow();
				timeline = new TimeLine();
				outline = new Outline();
				itemoptions = new ItemOption();
				srcWindow = new SourceWindow();
				if (AppProperties.loadProperties(slvePath + "slve.init")) mainWindow.GO(outline, itemoptions, timeline);
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	public static MainWindow getMainWindow () {
		return mainWindow;
	}
	
	public static TimeLine getTimeLine () {
		return timeline;
	}
	
	public static Outline getOutline () {
		return outline;
	}
	
	public static ItemOption getItemOption() {
		return itemoptions;
	}
	
	public static SourceWindow getSourceWindow () {
		return srcWindow;
	}
	
	public static void makeJBar () {
		tree = new ArrayListWithName<Object>("tree");
		jmenubar = new JMenuBar ();
		for (SlveMenuItem item : baritems) {
			loopin(item);
		}
		looper(tree, "",null);
		getMainWindow().setJMenuBar(jmenubar);
	}
	
	private static void loopin (SlveMenuItem menu) {
		ArrayListWithName<Object> branch = tree;
		a:for (String str : menu.getPath()) {
			for (Object obj : branch) {
				//System.out.println(str + " " + ((ArrayListWithName) obj).getName());
				if (!(obj instanceof SlveMenuItem) && str.equals(((ArrayListWithName) obj).getName())) {
					branch = (ArrayListWithName) obj;
					continue a;
				}
			}
			ArrayListWithName<Object> newBranch = new ArrayListWithName<Object>(str);
			branch.add(newBranch);
			branch = newBranch;
		}
		branch.add(menu);
	}
	
	private static void looper (ArrayListWithName<Object> branch, String str, JMenu menu) {
		String string = "|->"+str;
		for (Object obj : branch) {
			try {
				System.out.println(string + "["+((SlveMenuItem) obj).getText()+"]");
				if (menu == null)
					jmenubar.add((SlveMenuItem) obj);
				else
					menu.add((SlveMenuItem) obj);
			} catch (ClassCastException e) { //fire if object is not a 'slveMenuItem'
				JMenu newmenu = new JMenu( ((ArrayListWithName) obj).getName() );
				System.out.println(string + ""+newmenu.getText()+" " + ((ArrayListWithName) obj).size());
				if (menu == null) {
					jmenubar.add(newmenu);
					looper((ArrayListWithName<Object>) obj,string,newmenu);
				} else {
					menu.add(newmenu);
					looper((ArrayListWithName<Object>) obj,string,newmenu);
				}
			}
		}
	}
	/*
	}
		JMenu jmenu = null;
		a:for (String str : menu.getPath()) {
			if (jmenu == null)
				for (int i = 0; i < barmenu.size();i++) {
					JMenu jmenu2 = (JMenu) barmenu.get(i);
					if (str.equals(jmenu2.getText())) {
						jmenu = jmenu2;
						continue a;
					}
				}
			else {
				System.out.println(jmenu.getText() + "  " + jmenu.getSubElements().length);
				for (int i = 0; i < jmenu.getSubElements().length;i++) {
					//show child elements
					for(MenuElement childMenuItem: jmenu.getSubElements()[i].getSubElements())
					{
						//New and Save here ...
						System.out.println("ChildMenu Name=>" + childMenuItem.getComponent().getName());
						MenuElement pop = jmenu.getSubElements()[i];
						JMenu jmenu2 = (JMenu) pop;
						if (str.equals(jmenu2.getText() )) {
							jmenu = jmenu2;
							continue a;

						}
					}
				}
			}
			JMenu jmenu3 = new JMenu(str);
			if (jmenu == null) 
				barmenu.add(jmenu3);
			else
				jmenu.add(jmenu3);
			jmenu = jmenu3;
		}
		if (jmenu == null) 
			barmenu.add(menu);
		else
			jmenu.add(menu);
	}*/
	
	public static void addMenuBarItem (SlveMenuItem item) {
		baritems.add(item);
	}
	
	public static void frontEveryFrame () {
		for (JFrame frame : frames) {
			frame.toFront();
		}
	}
	
	public static ArrayList<SlveFrame> getFrames () {
		return frames;
	}

}
