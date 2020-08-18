<#import "parts/general.ftl" as c>
<#import "parts/loginTemplate.ftl" as l>

<@c.page>
    <div>
        <@l.logout/>
    </div>
    <div>
        <div>Форма ввода
            <form method="post">
                <input type="text" name="text" placeholder="Введите тект"/>
                <input type="text" name="tag" placeholder="Введите тег"/>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button type="submit">Добавить сообщение</button>
            </form>
        </div>

        <div>Список сообщений</div>
        <#list messages as message>
            <div>
                <b>${message.id}</b>
                <span>${message.text}</span>
                <i>${message.tag}</i>
                <strong>${message.authorName}</strong>
            </div>
        <#else>
            No messages
        </#list>

        <div>
            <form method="get" action="/main">
                <input type="text" name="filter" value="${filter}"/>
                <button type="submit">Найти</button>
            </form>
        </div>
    </div>
</@c.page>