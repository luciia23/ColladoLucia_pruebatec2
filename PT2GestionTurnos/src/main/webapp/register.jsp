<%-- 
    Document   : register
    Created on : Mar 25, 2024, 11:35:17 AM
    Author     : lucia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Register</title>

        <!-- Custom fonts for this template-->
        <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">

        <!-- Custom styles for this template-->
        <link href="css/sb-admin-2.min.css" rel="stylesheet">

    </head>

    <body class="bg-gradient-primary">

        <div class="container">

            <div class="card o-hidden border-0 shadow-lg my-5">
                <div class="card-body p-0">
                    <!-- Nested Row within Card Body -->
                    <div class="row">
                        <div class="col-lg-5 d-none d-lg-block bg-register-image"></div>
                        <div class="col-lg-7">
                            <div class="p-5">
                                <div class="text-center">
                                    <h1 class="h4 text-gray-900 mb-4">Crea una Cuenta!</h1>
                                </div>
                                <% if (request.getAttribute("errorMessage") != null) {%>
                                <div class="alert alert-danger" role="alert">
                                    <%= request.getAttribute("errorMessage")%>
                                </div>
                                <% }%>

                                <form class="user" action="SvRegister" method="POST">
                                    <div class="form-group row">
                                        <div class="col-sm-6 mb-3 mb-sm-0">
                                            <input type="text" class="form-control form-control-user" id="name" required name="name"
                                                   placeholder="Nombre">
                                        </div>
                                        <div class="col-sm-6">
                                            <input type="text" class="form-control form-control-user" id="lastName" required name="lastName"
                                                   placeholder="Apellido">
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <div class="col-sm-6 mb-3 mb-sm-0">
                                            <input type="text" class="form-control form-control-user"
                                                   pattern="[0-9]{8}[A-Za-z]" id="dni" name="dni" required
                                                   placeholder="DNI">
                                        </div>
                                        <div class="col-sm-6">
                                            <select class="form-control form-select-user" id="role" required name="role">
                                                <option selected disabled value="">Selecciona Tipo Usuario</option>
                                                <option value="Admin">Admin</option>
                                                <option value="Basic">Usuario Básico</option>
                                            </select>
                                        </div>

                                    </div>

                                    <div class="form-group row">
                                        <div class="col-sm-6 mb-3 mb-sm-0">
                                            <input type="password" class="form-control form-control-user"
                                                   id="password" name="password" required placeholder="Password">
                                        </div>
                                    </div>
                                    <button class="btn btn-primary btn-user btn-block"> Registrar Cuenta </button>
                                    <hr>
                                </form>
                                <hr>
                                <div class="text-center">
                                    <a class="small" href ="logout">¿Quieres probar tu nueva cuenta? ¡Vuelve al Login!</a>
                                </div>
                                <div class="text-center">
                                    <a class="small" href="index.jsp" >¿Volver a la página principal?</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>

        <!-- Bootstrap core JavaScript-->
        <script src="vendor/jquery/jquery.min.js"></script>
        <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

        <!-- Core plugin JavaScript-->
        <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

        <!-- Custom scripts for all pages-->
        <script src="js/sb-admin-2.min.js"></script>

    </body>

</html>
