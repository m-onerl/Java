<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns="http://www.w3.org/TR/xhtml1/strict">
<xsl:output indent="yes" method="html" doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN" doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"/>

<xsl:template match="/PLAY">
<html>
  <body>
	  <xsl:apply-templates/>
  </body>
</html>
</xsl:template>

<xsl:template match="TITLE">
	<h1><left><xsl:value-of select="."/></left></h1>
</xsl:template>

<xsl:template match="FM">
	  <span style="display:block;">
		  <xsl:for-each select="P">
			  <div><xsl:value-of select="."/></div>
		  </xsl:for-each>
	  </span>
</xsl:template>

<xsl:template match="PERSONAE">
	<xsl:value-of select="PERSONAE/TITLE"/>
	<ul>
		<xsl:apply-templates/>
	</ul>
</xsl:template>

<xsl:template match="PERSONA">
	<li><xsl:value-of select="."/></li>
</xsl:template>

<xsl:template match="PGROUP">
	<li>
		<xsl:value-of select="GRPDESCR"/>
		<ul>
		  <xsl:for-each select="PERSONA">
			  <div><xsl:value-of select="."/></div>
		  </xsl:for-each>
		</ul>
	</li>
</xsl:template>

<xsl:template match="SCNDESCR">
	<h4><xsl:value-of select="."/></h4>
</xsl:template>

<xsl:template match="ACT">
	<!--h2><xsl:value-of select="TITLE"/></h2-->
	<h3><xsl:value-of select="SUBTITLE"/></h3>
	<i><xsl:value-of select="PROLOGUE"/></i>
	<i><xsl:value-of select="EPILOGUE"/></i>
	<center>
		<xsl:apply-templates/>
	</center>
</xsl:template>

<xsl:template match="SCENE">
	<!--h3><xsl:value-of select="TITLE"/></h3-->
	<h4><xsl:value-of select="SUBTITLE"/></h4>
	<xsl:apply-templates/>
</xsl:template>

<xsl:template match="STAGEDIR">
	-- <xsl:value-of select="."/> --
</xsl:template>

<xsl:template match="SUBHEAD">
	<b><xsl:value-of select="."/></b>
</xsl:template>

<xsl:template match="SPEECH">
	<p>
	<xsl:apply-templates/>
	</p>
</xsl:template>

<xsl:template match="SPEAKER">
	<b><xsl:value-of select="."/></b>
	<br/>

</xsl:template>

<xsl:template match="LINE">
	<xsl:value-of select="."/>
	<br/>
</xsl:template>



</xsl:stylesheet>

