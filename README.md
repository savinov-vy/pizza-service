для запуска приложения необходимо установить:
- Docker
- Docker compose
- java 17

для запуска приложения необходимо поднять базу данных. Для этого выполните команду: <br>
- make up-db <br> 
- запустить приложение
- в браузере прейдите http://localhost:8080/ <br>
<p>логин/пароль: admin/admin</p>
<p style="margin-left: 101px; line-height: 0.1;">user/user</p><br>

для запуска всех тестов выполните в терминале:<br>
- make test          "для запуска всех тестов"<br>
- make integration   "для запуска integration тестов"<br>
- make unit          "для запуска unit тестов"<br>