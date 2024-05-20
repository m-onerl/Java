<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <body>
                <h2>Football Teams</h2>
                <xsl:for-each select="//team">
                    <div>
                        <h3><xsl:value-of select="name[@type='nick']"/></h3>
                        <p><strong>Location:</strong> <xsl:value-of select="location/city"/>, <xsl:value-of select="location/state"/>, <xsl:value-of select="location/country"/></p>
                        <p><strong>Venue:</strong> <xsl:value-of select="season-details/venue/name[@type='short']"/> (<xsl:value-of select="season-details/venue/location/city"/>)</p>
                        <p><strong>Capacity:</strong> <xsl:value-of select="season-details/venue/season-details/capacity"/></p>
                    </div>
                </xsl:for-each>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
