//@author hcadavid

apimock=(function(){

	var mockdata=[];

	mockdata["johnconnor"]=	[{author:"johnconnor","points":[{"x":150,"y":120},{"x":215,"y":115}],"name":"house"},
	 {author:"johnconnor","points":[{"x":340,"y":240},{"x":15,"y":215}],"name":"gear"}];
	mockdata["maryweyland"]=[{author:"maryweyland","points":[{"x":140,"y":140},{"x":115,"y":115}],"name":"house2"},
	 {author:"maryweyland","points":[{"x":140,"y":140},{"x":115,"y":115}],"name":"gear2"}];
	mockdata["Camilo"]=[{author:"Camilo","points":[{"x":140,"y":140},{"x":115,"y":115},{"x":1,"y":115}],"name":"house1"},
	 {author:"Camilo","points":[{"x":140,"y":140},{"x":115,"y":115}],"name":"gear1"}];
     	mockdata["MariaPaula"]=[{author:"MariaPaula","points":[{"x":140,"y":140},{"x":115,"y":115}],"name":"house3"},
	 {author:"MariaPaula","points":[{"x":140,"y":140},{"x":115,"y":115}],"name":"gear3"}];
     	mockdata["Karen"]=[{author:"Karen","points":[{"x":140,"y":140},{"x":115,"y":115}],"name":"house4"},
	 {author:"Karen","points":[{"x":140,"y":140},{"x":115,"y":115}],"name":"gear4"}];
     	mockdata["Laura"]=[{author:"Laura","points":[{"x":140,"y":140},{"x":115,"y":115}],"name":"house5"},
	 {author:"Laura","points":[{"x":140,"y":140},{"x":115,"y":115}],"name":"gear5"}];

	return {
		getBlueprintsByAuthor:function(authname,callback){
			callback(
				mockdata[authname]
			);
		},

		getBlueprintsByNameAndAuthor:function(authname,bpname,callback){

			callback(
				mockdata[authname].find(function(e){return e.name===bpname})
			);
		}
	};	

})();

/*
Example of use:
var fun=function(list){
	console.info(list);
}

apimock.getBlueprintsByAuthor("johnconnor",fun);
apimock.getBlueprintsByNameAndAuthor("johnconnor","house",fun);*/