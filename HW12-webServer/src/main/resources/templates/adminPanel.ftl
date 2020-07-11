<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Пользователи</title>
    <style>
        input {
            margin-top: 3px;
            margin-bottom: 3px;
        }
        </style>

    <script type="text/javascript">
        function showDiv(select) {
            var selectedValue = select.options[select.selectedIndex].value;
            var allUsersDiv = document.getElementById("allUsers");
            var addUserDiv = document.getElementById("addUser");
            if (selectedValue === 'show') {
                allUsersDiv.style.display = "block";
                addUserDiv.style.display = "none";
            }
            if (selectedValue === 'add') {
                addUserDiv.style.display = "block";
                allUsersDiv.style.display = "none";
            }
        }
        function createUser() {
            const userName = document.getElementById('name').value;
            const userLogin = document.getElementById('login').value;
            const userPassword = document.getElementById('password').value;
            const user = {name: userName, login: userLogin, password: userPassword};
            fetch('api/users/', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(user)})
            .then(alert("Пользователь создан"));
        }
    </script>
</head>

<body>
<h4>Выберите действие</h4>
<select id="main_decision" onchange="showDiv(this)">
    <option value="show">Получить список пользователей</option>
    <option value="add">Создать пользователя</option>
</select>

<div id="allUsers">
    <h4>Список пользователей</h4> <button onclick="location.reload()">Обновить</button>
    <table style="width: 400px">
        <thead>
        <tr>
            <td style="width: 50px"><b>Id</b></td>
            <td style="width: 150px"><b>Имя</b></td>
            <td style="width: 100px"><b>Пароль</b></td>
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
</div>


<div id="addUser"  style="display: none;">
    <form>
        <p>Введите данные пользователя</p>
        <input type="text" name="name" id="name" maxlength="20" size="30" required placeholder="Введите ФИО пользователя"><br/>
        <input type="text" name="login" id="login" maxlength="20" size="30" required placeholder="Введите логин пользователя"><br/>
        <input type="text" name="password" id="password" maxlength="20" size="30" required placeholder="Введите пароль пользователя"><br/>
        <button onclick="createUser()">Создать</button>
    </form>
</div>
</body>
</html>
