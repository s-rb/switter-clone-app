<#import "parts/general.ftl" as c>
<#import "parts/loginTemplate.ftl" as l>

<@c.page>
<#--    Если есть сессия (только для зарегистрированного пользователя) и если есть-->
    <#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
        <div class="alert alert-danger" role="alert">
            ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
        </div>
    </#if>
    <#if message??>
        <div class="alert alert-${messageType}" role="alert">
            ${message}
        </div>
    </#if>
    <@l.login "/login" false/>
</@c.page>