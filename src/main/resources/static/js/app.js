/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* global apimock */
/* global apiclient */


var app = (function () {
    var api = apiclient;
    var _nomAuthor;
    var _bpName;
    var _puntos = [];
    var _BP;

    //nombre del autor seleccionado
    var nombreAutor = function (author) {
        _nomAuthor = document.getElementById("autor").value;
        //alert(_nomAuthor);
    };

    var Gsuma = function (suma, bp) {
        return suma + bp.puntos;
    };
    // convierta sus elementos a objetos con sólo el nombre y el número de puntos.
    function convertirNombrePuntos(blues) {
        return {"name": blues.name, "puntos": blues.points.length};
    }

    // tome cada uno de estos elementos, y a través de jQuery agregue un elemento <tr> (con los respectvos <td>)
    function agregarElementoTabla(elem) {
        return "<tr><td>" + elem.name + "</td><td>" + elem.puntos + "</td><td>\n\
                <button type='button' onclick=app.dibujarPlanos(document.getElementById('autor').value,'" + elem.name + "') > Open </button></td></tr>";
    }
    var BluePrint = function () {
        this.name = _bpName;
        this.author = _nomAuthor;
        this.points = _puntos;
    };
    var realizarPut = function () {
        var bps = new BluePrint();
        var put = api.chainedPromises(bps);
        put.then(
                function () {
                    console.info("OK");
                },
                function () {
                    console.info("ERROR");
                });
        return put;
    };



    return{
        //ver autor seleccionado
        verAutor: function (author) {
            nombreAutor(author);
        },
        //actualizar listado segun autor seleccionado
        actualizarListadoPlanos: function (author) {
            app.verAutor(author);
            api.getBlueprintsByAuthor(author, function (bpname) {
                // map convierta sus elementos a objetos con sólo el nombre y el número de puntos.
                var newbpname = bpname.map(convertirNombrePuntos);
                var updatebpname = newbpname.map(agregarElementoTabla);
                $("table tbody tr").remove();
                $("table tbody").append(updatebpname);
                //aplique un 'reduce' que calcule el número de puntos
                $("#totalPuntos").text("Total user points: " + newbpname.reduce(Gsuma, 0));
            });

        },
        init: function () {
            //calculo clicks realizados
            var canvas = document.getElementById('myCanvas');
            var ctx = canvas.getContext("2d");
            var client = canvas.getBoundingClientRect();
            //$("#titulo").text("¡¡ CREANDO UN NUEVO PLANO !!");
            ctx.beginPath();
            ctx.moveTo(0, 0);
            if (window.PointerEvent) {
                canvas.addEventListener("pointerdown", function (event) {
                    if (_bpName !== undefined) {
                        var x = parseInt(event.clientX) - parseInt(client.left);
                        var y = parseInt(event.clientY) - parseInt(client.top);
                        _puntos[_puntos.length] = {"x": x, "y": y};
                        //alert('pointerdown at ' + (event.clientX - client.left) + ',' + (event.clientY - client.top));
                        ctx.lineTo(parseInt(event.clientX) - parseInt(client.left), parseInt(event.clientY) - parseInt(client.top));
                        ctx.stroke();
                    }
                });
            }
            ctx.closePath();
        },
        put: function () {
            realizarPut()
                    .then(app.actualizarListadoPlanos(document.getElementById('autor').value));
        },
        Delete: function () {
            _BP = new BluePrint();
            var canvas = document.getElementById('myCanvas');
            canvas.width = "500";
            api.Borrar(_BP)
                    .then($("table tbody tr").remove())
                    .then(app.actualizarListadoPlanos(document.getElementById('autor').value));
        },
        dibujarPlanos: function (author, namePlano) {
            api.getBlueprintsByNameAndAuthor(author, namePlano, function (blueprint) {
                _bpName = namePlano;
                $("#titulo").text("¡¡ CREANDO UN NUEVO PLANO !!");
                var canvas = document.getElementById('myCanvas');
                canvas.width = "500";
                var puntos = blueprint.points;
                _puntos = puntos;
                var ax = 0;
                var ay = 0;
                var primera = true;
                var ctx = canvas.getContext("2d");
                ctx.beginPath();
                puntos.map(function (punto1) {
                    if (primera) {
                        ax = punto1.x;
                        ay = punto1.y;
                        primera = false;
                        ctx.moveTo(ax, ay);
                    } else {
                        ctx.lineTo(punto1.x, punto1.y);
                        ctx.stroke();
                    }
                });
                var client = canvas.getBoundingClientRect();
                if (window.PointerEvent) {
                    canvas.addEventListener("pointerdown", function (event) {
                        //alert('pointerdown at ' + (event.clientX - client.left) + ',' + (event.clientY - client.top));
                        ctx.lineTo(parseInt(event.clientX) - parseInt(client.left), parseInt(event.clientY) - parseInt(client.top));
                        ctx.stroke();
                    });
                }
                $("#titulo").text("Blueprints Draw: " + namePlano);
            });
        }

    };
})();