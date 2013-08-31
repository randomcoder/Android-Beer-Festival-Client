/**
 * Copyright (C) 2013 - RandomCoder <randomcoder@randomcoding.co.uk>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contributors:
 *    RandomCoder <randomcoder@randomcoding.co.uk> - initial API and implementation and/or initial documentation
 */
package uk.co.randomcoding.android.beerfestival.model.brewer

import scala.xml.NodeSeq
import scala.xml.Elem

/**
 * Brief description of TestBrewers
 *
 * @author RandomCoder
 *
 */
object TestBrewers {

  def surround(xml: NodeSeq): Elem = <object>
                                       <element name="outcome" type="string" value="success"/>
                                       <element name="result" type="array">
                                         { xml }
                                       </element>
                                     </object>

  val brewer4TsXml = <item index="0" type="object">
                       <element name="Festival" type="string" value="WOR/2013"/>
                       <element name="Id" type="string" value="4TS"/>
                       <element name="Name" type="string" value="4Ts"/>
                       <element name="Location" type="string" value="Runcorn"/>
                       <element name="County" type="string" value="Cheshire"/>
                       <element name="Postcode" type="string" value="WA7 4UA"/>
                       <element name="Description" type="string" value="4Ts (The Tavern Tasty Tipples) began brewing in 2010. The 0.5-barrel plant is located in a private garage and a further five-barrel plant is also used at a location in Runcorn. Beer are usually available in the Tavern, Warrington."/>
                     </item>

  val brewer4Ts = Brewer("4TS", "4Ts", "Runcorn, Cheshire. WA7 4UA", "4Ts (The Tavern Tasty Tipples) began brewing in 2010. The 0.5-barrel plant is located in a private garage and a further five-barrel plant is also used at a location in Runcorn. Beer are usually available in the Tavern, Warrington.")

  val brewerAbbeydaleXml = <item index="1" type="object">
                             <element name="Festival" type="string" value="WOR/2013"/>
                             <element name="Id" type="string" value="ABBEYDAL"/>
                             <element name="Name" type="string" value="Abbeydale"/>
                             <element name="Location" type="string" value="Sheffield"/>
                             <element name="County" type="string" value="S. Yorkshire"/>
                             <element name="Postcode" type="string" value="S8 0YX"/>
                             <element name="Description" type="string" value="Since starting in 1996, Abbeydale Brewery has grown steadily; it now produces upwards of 130 barrels a week, and recent investment has enabled further growth. The regular range is complemented by ever-changing seasonals - see website."/>
                           </item>

  val brewerAbbeydale = Brewer("ABBEYDAL", "Abbeydale", "Sheffield, S. Yorkshire. S8 0YX", "Since starting in 1996, Abbeydale Brewery has grown steadily; it now produces upwards of 130 barrels a week, and recent investment has enabled further growth. The regular range is complemented by ever-changing seasonals - see website.")

  val brewerAdnamsXml = <item index="2" type="object">
                          <element name="Festival" type="string" value="WOR/2013"/>
                          <element name="Id" type="string" value="ADNAMS"/>
                          <element name="Name" type="string" value="Adnams"/>
                          <element name="Location" type="string" value="Southwold"/>
                          <element name="County" type="string" value="Suffolk"/>
                          <element name="Postcode" type="string" value="IP18 6JW"/>
                          <element name="Description" type="string" value="The company was founded by George and Ernest Adnams in 1872, who were joined by the Loftus family in 1902; a member of each family is still a director of the company. Real ale is available in all 70 pubs and there is national distribution. All beers are now from a new energy-efficient 300-barrel brewery, built within the confines of the present site. Seasonal beers: see website. Bottle-conditioned beers are also available."/>
                        </item>

  val brewerAdnams = Brewer("ADNAMS", "Adnams", "Southwold, Suffolk. IP18 6JW", "The company was founded by George and Ernest Adnams in 1872, who were joined by the Loftus family in 1902; a member of each family is still a director of the company. Real ale is available in all 70 pubs and there is national distribution. All beers are now from a new energy-efficient 300-barrel brewery, built within the confines of the present site. Seasonal beers: see website. Bottle-conditioned beers are also available.")

  val producerBulmersXml = <item index="0" type="object">
                             <element name="Festival" type="string" value="WOR/2013"/>
                             <element name="Id" type="string" value="BULMER"/>
                             <element name="Name" type="string" value="Bulmer's"/>
                             <element name="Location" type="string" value="Hereford"/>
                             <element name="Description" type="string" value="Big cider producer"/>
                           </item>

  val producerBulmers = Brewer("BULMER", "Bulmer's", "Hereford", "Big cider producer")
}