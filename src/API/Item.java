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
package API;

import java.util.ArrayList;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JPanel;

import start.AppProperties;
import start.MainWindow;
import start.Start;
import tools.TimeLine;
import exceptions.NoItemFoundException;

public class Item {
	
	protected ArrayList<String>  keyFrameTranslation = new ArrayList<String>();
	protected ArrayList<String>  keyFrameRotation = new ArrayList<String>();
	protected ArrayList<Integer> keyFrameActiv = new ArrayList<Integer>();                   //<--------activation
	protected String m_posX = "", m_posY = "", m_width = "", m_height = "", m_rotation = "";
	protected int cachePosX, cachePosY, cacheWidth, cacheHeight, cacheRotation;
	protected boolean cacheOn = true;
	protected int m_id;
	protected double m_ratio;
	protected String m_name;
	protected boolean movable;
	protected Mod parentMod; // Every item come from either a mod or slve directly
						//This tells us from where it comes from
						//It also allows same id's from different mod
	
	public Item () {}
	
	public Item (String name) {
		m_name = name;
		cache();
	}
	
	public Item (String name, String parentModName) {
		m_name = name;
		for (Mod mod : AppProperties.getMods()) {
			parentMod = mod;
		}
		cache();
	}
	
	public Mod getParentMod () {
		return parentMod;
	}
	
	public void setMovable (boolean b) {
		movable = b;
	}
	
	public boolean isMovable () {
		return movable;
	}
	
	public int getPosXFromFormula () {
		String str = calculeVariable(m_posX);
		if (!str.equals("!")) {
			return (int) Double.parseDouble(str);
		} else 
			return 0;
	}
	
	public int getPosYFromFormula () {
		String str = calculeVariable(m_posY);
		if (!str.equals("!"))
			return (int) Double.parseDouble(str);
		else 
			return 0;
	}
	
	public int getWidthFromFormula () {
		String str = calculeVariable(m_width);
		if (!str.equals("!"))
			return (int) Double.parseDouble(str);
		else 
			return 0;
	}
	
	public int getHeightFromFormula () {
		String str = calculeVariable(m_height);
		if (!str.equals("!"))
			return (int) Double.parseDouble(str);
		else 
			return 0;
	}
	
	public int getWidth () {
		return cacheWidth;
	}
	
	public int getHeight () {
		return cacheHeight;
	}
	
	public int getPosX () {
		return cachePosX;
	}
	
	public int getPosY () {
		return cachePosY;
	}
	
	public int getRotation () {
		return cacheRotation;
	}
	
	public void setPosX (int i) {
		m_posX = i+"";
		cachePosX = i;
	}
	
	public void setPosY (int i) {
		m_posY = i+"";
		cachePosY = i;
	}
	
	public void setWidth (int i) {
		m_width = i+"";
		cacheWidth = i;
	}
	
	public void setHeight (int i) {
		m_height = i+"";
		cacheHeight = i;
	}
	
	public void setName (String name) {
		m_name = name;
	}
	
	public JPanel getOption (int w, int h) {
		JPanel cont = new JPanel();
		//cont.setPreferredSize(new Dimension(w, h));
		return cont;
	}
	
	/**
	 * return false if something went wrong
         * @return 
	 */
	public boolean cache () {
		boolean isEveryThingsRight = true;
		try {
			cachePosX = (int) Double.parseDouble(calculeVariable(m_posX));
		} catch (NumberFormatException e) {
			cachePosX = 0;
			isEveryThingsRight = false;
		}
		try {
			cachePosY = (int) Double.parseDouble(calculeVariable(m_posY));
		} catch (NumberFormatException e) {
			cachePosY = 0;
			isEveryThingsRight = false;
		}
		try {
			cacheWidth = (int) Double.parseDouble(calculeVariable(m_width));
		} catch (NumberFormatException e) {
			cacheWidth = 0;
			isEveryThingsRight = false;
		}
		try {
			cacheHeight = (int) Double.parseDouble(calculeVariable(m_height));
		} catch (NumberFormatException e) {
			cacheHeight = 0;
			isEveryThingsRight = false;
		}
		try {
			cacheRotation = (int) Double.parseDouble(calculeVariable(m_rotation));
		} catch (NumberFormatException e) {
			cacheRotation = 0;
			isEveryThingsRight = false;
		}
		{
			cacheOn = cacheActiv();
		}
		return isEveryThingsRight;
	}
	
	public String getName () {
		return m_name;
	}
	
	public String getInfo () {
		return m_name + "-> x:" + cachePosX + " y:" + cachePosY + " w:" + cacheWidth + " h:" + cacheHeight + " r:" + cacheRotation;
	}
	
	public void setId (int i) {
		m_id = i;
	}
	
	public int getId () {
		return m_id;
	}
	
	public void setRotation (int i) {
		m_rotation = i+"";
		cacheRotation = i;
	}
	
	public int getRotationFormFormula () {
		String str = calculeVariable(m_rotation);
		if (!str.equals("!"))
			return (int) Double.parseDouble(str);
		else 
			return 0;
	}
	
	public double getRatio () {
		return m_ratio;
	}
	
	public String getPosXFormula () {
		return m_posX;
	}
	
	public String getPosYFormula () {
		return m_posY;
	}
	
	public String getWidthFormula () {
		return m_width;
	}
	
	public String getHeightFormula () {
		return m_height;
	}
	
	public String getRotationFormula () {
		return m_rotation;
	}
	
	public boolean setPosXFormula (String str) {
		m_posX = str;
		return cache();
	}
	
	public boolean setPosYFormula (String str) {
		m_posY = str;
		return cache();
	}
	
	public boolean setWidthFormula (String str) {
		m_width = str;
		return cache();
	}
	
	public boolean setHeightFormula (String str) {
		m_height = str;
		return cache();
	}
	
	public boolean setRotationFormula (String str) {
		m_rotation = str;
		return cache();
	}
	
	public boolean isActiv () {
		return cacheOn;
	}
	
	public boolean cacheActiv () {
		if (keyFrameActiv.size() > 0) {
			for (int index = 0; index < keyFrameActiv.size(); index++) {
				int testedTime = keyFrameActiv.get(index);

				if (testedTime > TimeLine.getTime()) {
					return !(index%2 == 0);
				}
			}
			return !(keyFrameActiv.size()%2 == 0);
		} else {
			return true;
		}
	}
	
	public boolean addKeyFrameActiv (int time) {
		int finalIndex = 0 ;
		
		try {
			if (time == getLastKeyFrameActiv(time)) return false;
		} catch (IndexOutOfBoundsException e) {
			
		}
		
		for (int index = 0; index < keyFrameActiv.size();index++) {
			int testedTime = keyFrameActiv.get(index);
			if (time < testedTime) {
				break;
			} else {
				finalIndex += 1;
			}
		}
		
		keyFrameActiv.add(finalIndex, time);
		return true;
	}
	
	public void deleteKeyFrameActiv (int time) {
		for (int index = 0; index < keyFrameActiv.size();index++) {
			int testedTime = keyFrameActiv.get(index);
			if (testedTime == time) 
				keyFrameActiv.remove(index);
		}
	}
	
	/**
	 * return the last ActivKeyFrame from time given, (if everything went fine). Otherwise it will return -1
         * @param i
         * @return 
	 */
	public int getLastKeyFrameActiv (int i) {
		for (int index = 0; index < keyFrameTranslation.size(); index++) {
			int testedTime = keyFrameActiv.get(index);
			
			if (testedTime > i) {
				return keyFrameActiv.get(index - 1);
			}
		}
		try {
			return keyFrameActiv.get(keyFrameActiv.size()-1);
		} catch (ArrayIndexOutOfBoundsException e) {
			return -1;
		}
	}
	
	/**
	 *return the next ActivkeyFrame from time given, (if everything went fine). Otherwise it will return -1
         * @param i
         * @return 
	 **/
	public int getNextKeyFrameActiv (int i) {
		for (int index = 0; index < keyFrameActiv.size(); index++) {
			int testedTime = keyFrameActiv.get(index);
			
			if (testedTime > i) {
				return keyFrameActiv.get(index);
			}
		}
		try {
			return keyFrameActiv.get(keyFrameActiv.size()-1);
		} catch (ArrayIndexOutOfBoundsException e) {
			return -1;
		}
	}
	
	public ArrayList<Integer> getAllKeyframeActiv () {
		return keyFrameActiv;
	}
	
	public Integer getKeyframeActiv (int i) {
		return keyFrameActiv.get(i);
	}
	
	public boolean addKeyFrameTranslate (int time,String x, String y, int type) {
		String str = getLastKeyFrameTranslate(time);
		try {
			if (str.substring(1, str.indexOf(':')).equals(time+"")) return false;
		} catch (StringIndexOutOfBoundsException e) {/*everything's fine :)*/}
		
		str = "";
		if (type == 1) str = "t"+time+":"+x+","+y;
		else str = "T" + time + ":" + x + "," + y;
		
		int T = Integer.parseInt(str.substring(1, str.indexOf(':')));
		int finalIndex = 0;
		
		a:for (int index = 0; index < keyFrameTranslation.size();index++) {
			int testedTime = Integer.parseInt(keyFrameTranslation.get(index).substring(1, keyFrameTranslation.get(index).indexOf(':')));
			if (T < testedTime) {
				break a;
			} else {
				finalIndex += 1;
			}
		}
		keyFrameTranslation.add(finalIndex, str);
		return true;
	}
	
	public String getKeyFrameTranslate (int i) {
		return keyFrameTranslation.get(i);
	}
	
	/**
	 * return the last translationkeyFrame from time given, (if everything went fine). Otherwise it will return "!"
	 */
	public String getLastKeyFrameTranslate (int i) {
		for (int index = 0; index < keyFrameTranslation.size(); index++) {
			String str = keyFrameTranslation.get(index);
			int testedTime = Integer.parseInt(str.substring(1,str.indexOf(':')));
			
			if (testedTime > i) {
				return keyFrameTranslation.get(index - 1);
			}
		}
		try {
			return keyFrameTranslation.get(keyFrameTranslation.size()-1);
		} catch (ArrayIndexOutOfBoundsException e) {
			return "!";
		}
	}
	
	/**
	 * return the next translationkeyFrame from time given, (if everything went fine). Otherwise it will return "!"
	 */
	public String getNextKeyFrameTranslate (int i) {
		for (int index = 0; index < keyFrameTranslation.size(); index++) {
			String str = keyFrameTranslation.get(index);
			int testedTime = Integer.parseInt(str.substring(1,str.indexOf(':')));
			
			if (testedTime > i) {
				return keyFrameTranslation.get(index);
			}
		}
		try {
			return keyFrameTranslation.get(keyFrameTranslation.size()-1);
		} catch (ArrayIndexOutOfBoundsException e) {
			return "!";
		}
	}
	
	public String[] getAllKeyFramesTranslation () {
		String[] str = new String[keyFrameTranslation.size()];
		for (int index = 0; index < keyFrameTranslation.size(); index++) {
			str[index] = keyFrameTranslation.get(index);
		}
		return str;
	}
	
	public void deleteKeyFrameTranslationAt (int time) {
		for (int index = 0; index < keyFrameTranslation.size();index++) {
			int testedTime = Integer.parseInt(keyFrameTranslation.get(index).substring(1, keyFrameTranslation.get(index).indexOf(':')));
			if (testedTime == time) 
				keyFrameTranslation.remove(index);
		}
	}
	
	public boolean addKeyFrameRotation (int time, String string, int type) {
		String str = getLastKeyFrameRotation(time);
		try {
			if (str.substring(1, str.indexOf(':')).equals(time+"")) return false;
		} catch (StringIndexOutOfBoundsException e) {}
		str = "r"+time+":"+string;
		
		int T = Integer.parseInt(str.substring(1, str.indexOf(':')));
		int finalIndex = 0;
		
		a:for (int index = 0; index < keyFrameRotation.size();index++) {
			int testedTime = Integer.parseInt(keyFrameRotation.get(index).substring(1, keyFrameRotation.get(index).indexOf(':')));
			if (T < testedTime) {
				break a;
			} else {
				finalIndex += 1;
			}
		}
		keyFrameRotation.add(finalIndex, str);
		return true;
	}
	
	/**
	 * return the last rotationkeyFrame from time given, (if everything went fine). Otherwise it will return "!"
	 */
	public String getLastKeyFrameRotation (int i) {
		for (int index = 0; index < keyFrameRotation.size(); index++) {
			String str = keyFrameRotation.get(index);
			int testedTime = Integer.parseInt(str.substring(1,str.indexOf(':')));
			
			if (testedTime > i) {
				return keyFrameRotation.get(index - 1);
			}
		}
		try {
			return keyFrameRotation.get(keyFrameRotation.size()-1);
		} catch (ArrayIndexOutOfBoundsException e) {
			return "!";
		}
	}
	
	/**
	 * return the next translationkeyFrame from time given, (if everything went fine). Otherwise it will return "!"
	 */
	public String getNextKeyFrameRotation (int i) {
		for (int index = 0; index < keyFrameRotation.size(); index++) {
			String str = keyFrameRotation.get(index);
			int testedTime = Integer.parseInt(str.substring(1,str.indexOf(':')));
			
			if (testedTime > i) {
				return keyFrameRotation.get(index);
			}
		}try {
			return keyFrameRotation.get(keyFrameRotation.size()-1);
		} catch (ArrayIndexOutOfBoundsException e) {
			return "!";
		}
	}	
	
	public void deleteKeyFrameRotationAt (int time) {
		for (int index = 0; index < keyFrameRotation.size();index++) {
			int testedTime = Integer.parseInt(keyFrameRotation.get(index).substring(1, keyFrameRotation.get(index).indexOf(':')));
			if (testedTime == time) 
				keyFrameRotation.remove(index);
		}
	}
	
	public String[] getAllKeyFramesRotation () {
		String[] str = new String[keyFrameRotation.size()];
		for (int index = 0; index < keyFrameRotation.size(); index++) {
			str[index] = keyFrameRotation.get(index);
		}
		return str;
	}
	
	public String getKeyFrameRotation (int i) {
		return keyFrameRotation.get(i);
	}
	
	public String calculeVariable (String str) {
		//System.out.println("calculevariable");
		if (str.isEmpty()) {
			return 0+"";
		} else {
			str = findAndChangeVariables(str);
			Object result;
			try {
				ScriptEngine engine = new ScriptEngineManager().getEngineByExtension("js");
				result = engine.eval(str);
				return result.toString();
			} catch (ScriptException e) {
				return ("!");
			}
		}
	}
	
	public String calculeVariableNoChange (String str) {
		if (str.isEmpty()) {
			return " ";
		} else {
			str = findAndChangeVariables(str);
			return str;
		}
	}
	
	public String findAndChangeVariables (String str) {
		boolean b;
		//System.out.println("findandchangevariable");
		do {
			b = false;
			if (str.contains("#time_frame")) {
				MainWindow.getTimeLine();
				str = str.replace("#time_frame", TimeLine.getTime()+"");
				b = true;
			}
			if (str.contains("#camera_width")) {
				str = str.replace("#camera_width", Start.getMainWindow().getCameraWidth()+"");
				b = true;
			}
			if (str.contains("#camera_height")) {
				str = str.replace("#camera_height", Start.getMainWindow().getCameraHeight()+"");
				b = true;
			}
			if (str.contains("#me_width")) {
				str = str.replace("#me_width", m_width+"");
				b = true;
			}
			if (str.contains("#me_height")) {
				str = str.replace("#me_height", m_height+"");
				b = true;
			}
			if (str.contains("#me_posX")) {
				str = str.replace("#me_posX", m_posX+"");
				b = true;
			}
			if (str.contains("#me_posY")) {
				str = str.replace("#me_posY", m_posY+"");
				b = true;
			}
			if (str.contains("#item_width(")) {
				try {
					String str1 = str.substring(str.indexOf("#item_width(") + 12, str.indexOf(')'));
					str = str.replace("#item_width(" + str1 + ")", MainWindow.getItemByName(str1).getWidth()+"");
					b = true;
				} catch (StringIndexOutOfBoundsException e) {
					break;
				} catch (NoItemFoundException e) {}
			}
			if (str.contains("#item_height(")) {
				try {
					String str1 = str.substring(str.indexOf("#item_height(") + 12, str.indexOf(')'));
					str = str.replace("#item_height(" + str1 + ")", MainWindow.getItemByName(str1).getWidth()+"");
					b = true;
				} catch (StringIndexOutOfBoundsException e) {
					break;
				} catch (NoItemFoundException e) {}
			}
			if (str.contains("#item_posX(")) {
				try {
					String str1 = str.substring(str.indexOf("#item_posX(") + 12, str.indexOf(')'));
					str = str.replace("#item_posX(" + str1 + ")", MainWindow.getItemByName(str1).getWidth()+"");
					b = true;
				} catch (StringIndexOutOfBoundsException e) {
					break;
				} catch (NoItemFoundException e) {}
			}
			if (str.contains("#item_posAbsciss(")) {
				try {
					String str1 = str.substring(str.indexOf("#item_posAbsciss(") + 12, str.indexOf(')'));
					str = str.replace("#item_posAbsciss(" + str1 + ")", MainWindow.getItemByName(str1).getWidth()+"");
					b = true;
				} catch (StringIndexOutOfBoundsException e) {
					break;
				} catch (NoItemFoundException e) {}
			}
			if (str.contains("#item_posY(")) {
				try {
					String str1 = str.substring(str.indexOf("#item_Y(") + 12, str.indexOf(')'));
					str = str.replace("#item_Y(" + str1 + ")", MainWindow.getItemByName(str1).getWidth()+"");
					b = true;
				} catch (StringIndexOutOfBoundsException e) {
					break;
				} catch (NoItemFoundException e) {}
			}
			if (str.contains("#item_posOrdinate(")) {
				try {
					String str1 = str.substring(str.indexOf("#item_posOrdinate(") + 12, str.indexOf(')'));
					str = str.replace("#item_posOrdinate(" + str1 + ")", MainWindow.getItemByName(str1).getWidth()+"");
					b = true;
				} catch (StringIndexOutOfBoundsException e) {
					break;
				} catch (NoItemFoundException e) {}
			}
			if (str.endsWith(" ")) {
				str = str.substring(0, str.length() - 1);
				b = true;
			}
		} while (b);
		return str;
	}

}