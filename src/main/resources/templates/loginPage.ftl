<#import "parts/general.ftl" as c>
<#import "parts/loginTemplate.ftl" as l>

<@c.page>
    Login page
    <@l.login "/login"/>
    <a href="/register">Register</a>
</@c.page>