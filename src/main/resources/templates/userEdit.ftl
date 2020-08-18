<#import "parts/general.ftl" as c>

<@c.page>
    User editor
    <form action="/user" method="post">
        <input type="text" value="${user.username}" name="username"/>
        <#list roles as role>
            <div>
<#--               Проверяем на наличие роли в ролях юзера, возвращаем boolean ${user.roles?seq_contains(role)}-->
                <label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}/>${role}</label>
            </div>
        </#list>
        <input type="hidden" value="${user.id}" name="userId"/>
        <input type="hidden" value="${_csrf.token}" name="_csrf"/>
        <button type="submit">Save</button>
    </form>
</@c.page>