//@author hcadavid
apiclient = (function () {
//PARA PROBAR EL EJEMPLO:
//-Analice qué hacen las funciones implementadas en el módulo, y revise cómo la función pública (invocada con el botón) las invoca 'secuencialmente'.
//-Use la aplicación (oprima el botón) y mire el resultado.
//-Cambie la función pública para que encadene las funciones a través de promesas.
//-Pruebe nuevamente el funcionamiento y analice por qué es diferente el resultado.
//-En la penúltima operación (GET), dañe la URL para que falle, y revise qué ocurre con la promesa final.
//private functions

    var request1Response = "";
    var request2Response = "";
    putForumPost = function (bp) {

        return $.ajax({
            url: "/blueprints/" + bp.author + "/" + bp.name,
            type: 'PUT',
            data: JSON.stringify(bp),
            contentType: "application/json"
        });

    };
    Delete = function (bp) {

        return $.ajax({
            url: "/blueprints/" + bp.author + "/" + bp.name,
            type: 'DELETE',
            data: JSON.stringify(bp),
            contentType: "application/json"
        });

    };
    //public functions
    return {

        chainedPromises: function (bp) {
            //With promises
            return putForumPost(bp);
        },
        Borrar: function (bp) {
            //With promises
            return Delete(bp);
        },
        getBlueprintsByAuthor: function (authname, callback) {
            $.get("/blueprints/" + authname, callback);
        },
        getBlueprintsByNameAndAuthor: function (authname, bpname, callback) {
            $.get("/blueprints/" + authname + "/" + bpname, callback);
        }
    };

})();
