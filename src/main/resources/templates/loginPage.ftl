<#import "parts/general.ftl" as c>
<#import "parts/loginTemplate.ftl" as l>

<@c.page>
    ${message?ifExists}
    <@l.login "/login" false/>
</@c.page>