<cfcomponent displayname="Controller" extends="ModelGlue.unity.controller.Controller" output="false">
	<!--- 
		Any function set up to listen for the onRequestStart message happens before any of the <event-handlers>.
		This is a good place to put things like session defaults.
	--->
	
	<cffunction name="onRequestStart" access="public" returnType="void" output="false">
	  <cfargument name="event" type="any">
	  <cfset variables.dsn = getModelGlue().getBean('reactorConfiguration').getDSN()>	
	
	</cffunction>

	<!--- 
		Any function set up to listen for the onQueueComplete message happens after all event-handlers are
		finished running and before any views are rendered.  This is a good place to load constants (like UDF
		libraries) that the views may need.
	--->
	<cffunction name="onQueueComplete" access="public" returnType="void" output="false">
	  <cfargument name="event" type="any">
	</cffunction>

	<!--- 
		Any function set up to listen for the onRequestStart message happens after views are rendered.
	--->
	<cffunction name="onRequestEnd" access="public" returnType="void" output="false">
	  <cfargument name="event" type="any">
	</cffunction>
	
	<cffunction name="getRandTestimonials" access="public" returnType="void" output="false">
	  <cfargument name="event" type="any">
	  
	 	 <cfset var dsn = getModelGlue().GETIOCCONTAINER().getBean('reactorConfiguration').getDSN()>
	  	<cfset var qryRandTestimonials = "">
	  	
	  	<cfquery name="qryRandTestimonials" datasource="#dsn#">
			SELECT * FROM cms_testimonial
			WHERE bPublished = 1
			ORDER BY RAND() Limit 2			
		</cfquery>
		<cfset arguments.event.setValue('qryTestimonials', qryRandTestimonials)>	  	
	  	
	</cffunction>
	
	<cffunction name="getContent" access="public" returnType="void" output="false">
	  <cfargument name="event" type="any">
	  
	 	<!--- arguments you can pass into this from the calling message --->
	 	<cfset var content_type = arguments.event.getArgument("type" ,"")>
		<cfset var retQuery = arguments.event.getArgument("queryName", "content")> <!--- Default this to "content" --->
		
		<cfset var random = arguments.event.getArgument("random", "false")>
		<cfset var maxrows = arguments.event.getArgument("maxrows", 1000)>
	 	<cfset var qryContentQuery = 0>
	 	
	 	<cfquery name="qryContentQuery" datasource="#variables.dsn#" result="qryContentResult">
		 	SELECT     cms_article.*
			FROM         cms_article INNER JOIN
                      cms_article_type ON cms_article.art_type_id = cms_article_type.type_id
		 	WHERE cms_article.bPublished = 1
			<!--- AND dtDisplay <= <cfqueryparam cfsqltype="cf_sql_timestamp" value="#Now()#"> --->
			
			<cfif Len(content_type)>
			AND cms_article_type.type_name = <cfqueryparam cfsqltype="cf_sql_varchar" value="#content_type#">
			</cfif>
			<cfif random>
			ORDER BY RAND() Limit #maxrows#
			</cfif>
		</cfquery>
	 		
	 	
		<cfset arguments.event.setValue(retQuery, qryContentQuery)> 
		<cfset arguments.event.setValue("result_" & retQuery, qryContentResult)>	
	</cffunction>
	
	<cffunction name="setValue" access="public" returnType="void" output="false">
	  <cfargument name="event" type="any">
	  
	  <cfset stArguments = arguments.event.getAllArguments()>
	  <cfloop collection="#stArguments#" item="arg">
			<cfset arguments.event.setValue(arg, stArguments[arg])>
		</cfloop>
	  
	 </cffunction>

	<cffunction name="getPage" access="public" returnType="void" output="false">
	  <cfargument name="event" type="any">
	  
	  	<cfset arguments.event.setValue('section', arguments.event.getValue('page', ""))>
	  
	  		
	  
	  
	  
	  
	  
	 </cffunction>


	<cffunction name="getParentPages" access="private" returntype="string" hint="returns a comma delimited list of parent pages, recursing upwards">
	
	
	</cffunction>
	
	<cffunction name="getChildPages" access="private" returntype="struct" hint="returns a structure of child pages">
	
	
	
	</cffunction>


</cfcomponent>