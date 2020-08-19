<#import "parts/general.ftl" as c>
<#import "parts/loginTemplate.ftl" as l>

<@c.page>
<#--    mb-1 - margin botton 1-->
    <div class="mb-1">Add new user</div>
    ${message?ifExists}
    <@l.login "/register" true/>
</@c.page>