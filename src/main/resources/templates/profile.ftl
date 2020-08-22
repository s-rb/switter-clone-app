<#import "parts/general.ftl" as c>

<@c.page>
    <h5>${username}</h5>
    ${message?ifExists}
    <form method="post">
            <div class="form-group row">
                <label class="col-sm-2 col-form-label"> Email:</label>
                <div class="col-sm-6">
                    <input type="email" name="email" class="form-control" placeholder="email"/></div>
            </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Password:</label>
            <div class="col-sm-6">
<#--                Поле email может быть пустым - добавлен фолбэк !''-->
                <input type="password" name="password" class="form-control" placeholder="password" value="${email!''}"/></div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary">Save</button>
    </form>
</@c.page>