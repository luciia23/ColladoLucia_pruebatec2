<%-- 
    Document   : createTurnCitizens
    Created on : Mar 26, 2024, 6:31:06 PM
    Author     : lucia
--%>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.pruebatec.pt2gestionturnos.logic.model.Turn"%>
<%@page import="com.pruebatec.pt2gestionturnos.logic.model.Procedure"%>
<%@page import="java.util.stream.Collectors"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../components/header.jsp"%>
<%@ include file="../components/bodymenu.jsp"%>

<!-- Page Heading -->
<div class="d-sm-flex align-items-center justify-content-between mb-4">
    <h1 class="h3 mb-0 text-gray-800">GESTIÓN TURNOS</h1>
</div>

<!-- Card Crear Turnos --> 
<div class="card shadow mb-4">
    <div class="card-header py-3">
        <h6 class="m-0 font-weight-bold text-primary">Crear Turno</h6>
    </div>
    <div class="card-body">
        <form action="SvCitizenSurname" method="GET" class="form-inline my-2 my-lg-0">
            <label for="procName" class="form-label">Búsqueda de ciudadano por apellido</label>
            <div class="input-group">
                <input type="text" class="form-control bg-light border-0 small" placeholder="Search for..."
                       aria-label="Search" aria-describedby="basic-addon2" name="searchSurname">
                <div class="input-group-append">
                    <button class="btn btn-primary" type="submit">
                        <i class="fas fa-search fa-sm"></i>
                    </button>
                </div>
            </div> 
        </form>

        <form action ="SvTurnAdmin" method="POST" class="row g-3 mt-3 needs-validation">
            <!-- Dynamic Dropdown to display search results -->
            <div class="form-group">
                <label for="searchResultsSelect">Resultados Búsqueda Ciudadano</label>
                <select class="form-control" id="searchResultsSelect" name="searchResultsSelect" required>
                    <% 
                    List<Citizen> searchResults = (List<Citizen>) request.getAttribute("searchResults");
                    if (searchResults != null && !searchResults.isEmpty()) {
                        String results = searchResults.stream()
                                        .map(c -> "<option value=" + c.getDni() + ">" +c.getDni() + " - "+  c.getName() + "</option>")
                                        .collect(Collectors.joining());
                    %>
                    <option selected disabled value="">Selecciona...</option>
                    <%= results%>
                    <%
                  } else {
                    %><option disabled>No hay resultados</option><%
                   }
                    %>
                </select>
            </div>

            <% if (request.getAttribute("procedures") != null) { %>
            <div class="col-md-3">
                <label for="procName" class="form-label">Tipo de trámite</label>
                <select class="form-control form-select-user" id="procName" required name="procName">
                    <option selected disabled value="">Selecciona...</option>
                    <% List<Procedure> procList = (List<Procedure>) request.getAttribute("procedures");
                        String options = procList.stream()
                                .map(proc -> "<option value=" + proc.getId() + ">" + proc.getDescription() + "</option>")
                                .collect(Collectors.joining());
                    %>
                    <%= options%>
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
                    <div >
                        <%= request.getAttribute("registroCorrecto")%>                    
                    </div>
                </div>
                <%}%>
            </div>
        </form> 
    </div>
</div>
<!-- Card Mostrar Turnos --> 
<div class="card shadow mb-4">
    <div class="card-header py-3">
        <h6 class="m-0 font-weight-bold text-primary">Mostrar Turnos</h6>
    </div>
    <div class="card-body">

        <div class="container-fluid">

            <form action="SvTurnAdmin" method="GET" class="row g-3 mt-3 needs-validation">
                <div class="row">
                    <div class="card shadow mb-4">
                        <div class="card-header py-3" style="width: 1000px;">
                            <h6 class="m-0 font-weight-bold text-primary">Filtrado</h6>
                        </div>
                        <div class="row mt-3">
                            <div class="col-md-3">
                                <label for="dateFilter" class="form-label">Fecha turno</label>
                                <input type="date" class="form-control" id="dateFilter" name="dateFilter">
                            </div>                                          

                            <div class="col-md-4">
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="radio" value="Atendido" name="state" id="stateAtendido">
                                    <label class="form-check-label" for="stateAtendido">
                                        Atendido
                                    </label>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-check form-check-inline ">
                                    <input class="form-check-input" type="radio" value="En espera" name="state" id="stateEnEspera">
                                    <label class="form-check-label" for="stateEnEspera">
                                        En espera
                                    </label>
                                </div>
                            </div>
                        </div>
                        <button type="submit" class="btn btn-primary">Mostrar</button>
                    </div>

                </div>


            </form>


            <!-- Resultados en tabla --> 
            <div class="results-table">
                <% if (request.getAttribute("results") != null) { %>
                <h3>Resultados:</h3>
                <table class="table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Fecha</th>
                            <th>Ciudadano</th>
                            <th>Trámite</th>
                            <th>Estado</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Turn turn : (List<Turn>) request.getAttribute("results")) {%>
                        <tr>
                            <td><%= turn.getId()%></td>
                            <td><%= turn.getDate()%></td>
                            <td><%= turn.getCitizen().getDni()%> - <%= turn.getCitizen().getName() %> <%=turn.getCitizen().getSurname() %></td>
                            <td><%=turn.getProcedure().getDescription() %></td>
                            <% String statusClass = turn.isCondition() ? "text-success" : "text-danger";%>
                            <td class="<%= statusClass %>">
                                <form action="SvTurnState" method="POST" style="all:unset;">
                                    <input type="hidden" name="turnId" value="<%= turn.getId() %>" />
                                    <button type="submit" name ="stateLink" value="<%= turn.isCondition() ? "Atendido" : "En Espera"%>"
                                            style="all:unset; cursor:pointer; color:inherit; display:block; width:100%; height:100%;">
                                        <%= turn.isCondition() ? "Atendido" : "En Espera" %>
                                    </button>
                                </form>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
                <% }%>
            </div>

        </div>

    </div>
</div>
<%@ include file="../components/footer.jsp"%>


