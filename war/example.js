var app = angular.module('myApp', ['ngAnimate', 'ui.bootstrap']);
app.controller('PaginationDemoCtrl', function($scope,$http) {
	var xMLHttpRequest = new XMLHttpRequest();
    $scope.data = [];
    $scope.filteredData = [];
    $scope.error = false;
    $scope.submit = function(){
    	if(!validate($scope.input)){
    		$scope.error = true;
    		return;
    	}
    	$scope.error = false;	
    	$http({method:'GET',
    			url:'/AjaxServlet?number='+$scope.input

    	}).then(function(response){
    		$scope.data=response.data.Number;
    		$scope.result = response.data.Count;
    		show();
    	} );
    };
    var validate = function(str){
    	return str.match(/^\d{7}$/)||str.match(/^\d{10}$/);
    };
    var show = function(){
        $scope.totalItems = $scope.data.length;
        $scope.itemsPerPage = 10;
        $scope.currentPage = 1;
        $scope.pageCount = function() {
            return Math.ceil($scope.data.length / $scope.itemsPerPage);
        };
        $scope.$watch('currentPage + itemsPerPage', function() {
            var begin = (($scope.currentPage - 1) * $scope.itemsPerPage),
                end = begin + $scope.itemsPerPage;
            $scope.filteredData = $scope.data.slice(begin, end);
        });
    };
});