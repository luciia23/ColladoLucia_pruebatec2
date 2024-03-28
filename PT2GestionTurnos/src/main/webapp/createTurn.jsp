<%-- 
    Document   : createTurn
    Created on : Mar 24, 2024, 11:27:12 PM
    Author     : lucia
--%>

<%@page import="com.pruebatec.pt2gestionturnos.logic.model.Procedure"%>
<%@page import="java.util.stream.Collectors"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.pruebatec.pt2gestionturnos.logic.model.Turn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="components/header.jsp"%>
<%@ include file="components/bodymenu.jsp"%>

<!-- Page Heading -->
<div class="d-sm-flex align-items-center justify-content-between mb-4">
    <h1 class="h3 mb-0 text-gray-800">GESTIÓN TURNOS</h1>
</div>

<div class="card shadow mb-4">
    <div class="card-header py-3">
        <h6 class="m-0 font-weight-bold text-primary">Crear Turno</h6>
    </div>
    <div class="card-body">
        <form action ="SvTurn" method="POST" class="row g-3 mt-3 needs-validation">
            <% if (request.getAttribute("procedures") != null) { %>
            <div class="col-md-3">
                <label for="procName" class="form-label">Tipo de trámite</label>
                <select class="form-control form-select-user" id="procName" required name="procName">
                    <option selected disabled value="">Selecciona...</option>
                    <% List<Procedure> procList = (List<Procedure>) request.getAttribute("procedures");
                        String procedures = procList.stream()
                                .map(proc -> "<option value=" + proc.getId() + ">" + proc.getDescription() + "</option>")
                                .collect(Collectors.joining());
                    %>
                    <%= procedures%>
                </select>
            </div>
            <% } %>
            <div class="col-md-3">
                <label for="procDate" class="form-label">Fecha del turno</label>
                <input type="date" class="form-control" id="procDate" name="procDate" required>
            </div>
            <div class="col-12">
                <br>
                <button class="btn btn-primary" type="submit">Registrar turno</button>
                <% if (request.getAttribute("registroCorrecto") != null) {%>
                <div class="alert alert-success d-flex align-items-center mt-3" role="alert">
                    <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Danger:"><use xlink:href="#exclamation-triangle-fill"/></svg>
                    <div>
                        <%= request.getAttribute("registroCorrecto")%>
                    </div>
                </div>
                <% } %>
            </div>
        </form>
    </div>
</div>

<div class="card shadow mb-4">
    <div class="card-header py-3">
        <h6 class="m-0 font-weight-bold text-primary">Mostrar Turnos</h6>
    </div>
    <div class="card-body">
        <form  action ="SvCitizen" method="GET" class="row g-3 mt-3 needs-validation">
            <button type="submit" class="btn btn-primary">Mostrar</button>
        </form>
        <!<!-- Resultados en tabla --> 
        <div class="results-table">
            <% if (request.getAttribute("turnsList") != null) { %>
            <h3>Resultados:</h3>
            <table class="table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Fecha</th>
                        <th>Trámite</th>
                        <th>Estado</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (Turn turn : (List<Turn>) request.getAttribute("turnsList")) {%>
                    <tr>
                        <td><%= turn.getId()%></td>
                        <td><%= turn.getDate()%></td>
                        <td><%= turn.getProcedure().getDescription()%></td>
                        <% String statusClass = turn.isCondition() ? "text-success" : "text-danger";%>
                        <td class="<%= statusClass%>"><%= turn.isCondition() ? "Atendido" : "En Espera"%></td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
            <% }%>
        </div>
    </div>
</div>
<%@ include file="components/footer.jsp"%>

