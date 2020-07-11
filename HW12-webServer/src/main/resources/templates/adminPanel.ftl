<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Пользователи</title>
    <script>
        function getUserById() {
            const userIdTextBox = document.getElementById('userIdTextBox');
            const userDataContainer = document.getElementById('userDataContainer');
            const id = userIdTextBox.value;
            //fetch('api/user?id=' + id)
            fetch('api/user/' + id)
                .then(response => response.json())
                .then(user => userDataContainer.innerHTML = JSON.stringify(user));
        }
    </script>
</head>

<body>
<h4>Выберите действие</h4>
<select>
    <option value="add">Создать пользователя</option>
    <option value="show">Получить список пользователей</option>
</select>
<#--<input type="text" id = "userIdTextBox" value="3" placeholder="Введите id пользователя">-->
<button onclick="getUserById()">Выполнить</button>
<pre id = "userDataContainer"></pre>

<h4>Список пользователей</h4>
<table style="width: 400px">
    <thead>
        <tr>
            <td style="width: 50px">Id</td>
            <td style="width: 150px">Имя</td>
            <td style="width: 100px">Пароль</td>
        </tr>
    </thead>
    <#list allUsers as user>
        <tr>
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.password}</td>
        </tr>
    </#list>


    </tbody>
</table>
</body>
</html>
