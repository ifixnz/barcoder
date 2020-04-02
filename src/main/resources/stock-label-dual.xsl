<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:barcode="org.krysalis.barcode4j.xalan.BarcodeExt"
                xmlns:common="http://exslt.org/common"
                xmlns:xalan="http://xml.apache.org"
                exclude-result-prefixes="barcode common xalan">
  <xsl:output method="xml" version="1.0" omit-xml-declaration="no" indent="yes"/>
  <xsl:template match="barcode">
    <fo:root font-family="sans-serif" font-size="8pt">
      <fo:layout-master-set>
        <fo:simple-page-master page-height="30mm" page-width="88mm"
                               margin="2mm 0mm 0mm 0mm" master-name="PageMaster">
          <fo:region-body margin="0mm"/>
        </fo:simple-page-master>
      </fo:layout-master-set>
      <fo:page-sequence master-reference="PageMaster">
        <fo:flow flow-name="xsl-region-body" >
          <fo:table>
            <fo:table-column column-width="44mm"/>
            <fo:table-column column-width="44mm"/>
            <fo:table-body>
              <fo:table-row>
                <fo:table-cell>
                  <fo:block text-align="center">
                    <fo:instream-foreign-object>
                      <xsl:variable name="bc" select="barcode:generate(., code)"/>
                      <svg:svg xmlns:svg="http://www.w3.org/2000/svg">
                        <xsl:attribute name="width">
                          <xsl:value-of select="$bc/svg:svg/@width"/>
                        </xsl:attribute>
                        <xsl:attribute name="height">
                          <xsl:value-of select="$bc/svg:svg/@height"/>
                        </xsl:attribute>
                        <svg:rect x="5mm" y="5mm" fill="white">
                          <xsl:attribute name="width">
                            <xsl:value-of select="$bc/svg:svg/@width"/>
                          </xsl:attribute>
                          <xsl:attribute name="height">
                            <xsl:value-of select="$bc/svg:svg/@height"/>
                          </xsl:attribute>
                        </svg:rect>
                        <xsl:copy-of select="$bc"/>
                      </svg:svg>
                    </fo:instream-foreign-object>
                  </fo:block>
                  <fo:block text-align="center">
                    <xsl:value-of select="concat('Category: ', category)"/>
                  </fo:block>
                  <fo:block text-align="center">
                    <xsl:value-of select="description"/>
                  </fo:block>
                </fo:table-cell>
                <fo:table-cell>
                  <fo:block text-align="center">
                    <fo:instream-foreign-object>
                      <xsl:variable name="bc" select="barcode:generate(., code)"/>
                      <svg:svg xmlns:svg="http://www.w3.org/2000/svg">
                        <xsl:attribute name="width">
                          <xsl:value-of select="$bc/svg:svg/@width"/>
                        </xsl:attribute>
                        <xsl:attribute name="height">
                          <xsl:value-of select="$bc/svg:svg/@height"/>
                        </xsl:attribute>
                        <svg:rect x="5mm" y="5mm" fill="white">
                          <xsl:attribute name="width">
                            <xsl:value-of select="$bc/svg:svg/@width"/>
                          </xsl:attribute>
                          <xsl:attribute name="height">
                            <xsl:value-of select="$bc/svg:svg/@height"/>
                          </xsl:attribute>
                        </svg:rect>
                        <xsl:copy-of select="$bc"/>
                      </svg:svg>
                    </fo:instream-foreign-object>
                  </fo:block>
                  <fo:block text-align="center">
                    <xsl:value-of select="concat('Category: ', category)"/>
                  </fo:block>
                  <fo:block text-align="center">
                    <xsl:value-of select="description"/>
                  </fo:block>
                </fo:table-cell>
              </fo:table-row>
            </fo:table-body>
          </fo:table>
        </fo:flow>
      </fo:page-sequence>
    </fo:root>
  </xsl:template>
</xsl:stylesheet>
