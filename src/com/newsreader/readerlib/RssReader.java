/*
 * Copyright (C) 2011 Mats Hofman <http://matshofman.nl/contact/>
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

package com.newsreader.readerlib;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class RssReader {

    public static RssFeed read(URL url) throws SAXException, IOException, FileNotFoundException  {
        URL url = new URL(url);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("tmp.txt", Context.MODE_PRIVATE));
        String inputLine;
        while ((inputLine = in.readLine()) != null){
            inputLine = inputLine.replaceFirst('<link rel="alternate"','<mainlink ');
			outputStreamWriter.write(inputLine);
		}
        in.close();
		outputStreamWriter.close();
		InputStream inputStream = openFileInput("tmp.txt");
		return inputStream;
        //return read(url.openStream());
    }
    
    public static RssFeed read(String s) throws SAXException, IOException{
    	return read(new FileInputStream(new File(s)));
    }
        
    public static RssFeed read(InputStream stream) throws SAXException, IOException, FileNotFoundException  {

        try {

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            RssHandler handler = new RssHandler();
            InputSource input = new InputSource(stream);

            reader.setContentHandler(handler);
            
            reader.parse(input);

            return handler.getResult();

        } catch (ParserConfigurationException e) {
            throw new SAXException();
        }

    }

}
