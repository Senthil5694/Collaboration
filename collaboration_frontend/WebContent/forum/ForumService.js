'use strict';
 
app.factory('ForumService', ['$http', '$q','$rootScope', function($http, $q,$rootScope){
	
	console.log("ForumService...")
	
	var BASE_URL='http://localhost:8080/collaboration_backend'
		
    return {
         
            fetchAllForums: function() {
            	console.log("calling fetchAllForums ")
                    return $http.get(BASE_URL+'/forums')
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while fetching Forums');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            
             
            createForum: function(Forum){
            	console.log("calling create forum")
                    return $http.post(BASE_URL+'/createforums/', Forum)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while creating forum');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
        
            
           
         
    };
 
}]);