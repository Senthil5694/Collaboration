'use strict';
console.log(" start of forumController...")
app
		.controller(
				'ForumController',
				[
						'$scope',
						'ForumService',
						'$location',
						'$rootScope',
						'$http',
						function($scope, ForumService, $location, $rootScope,
								$http) {
							console.log("ForumController...")
							var self = this;
							self.Forum = {
								userid : '',
								id : '',
								title: '',
								description: '',
								status : '',
								reason : '',
								errorcode : '',
								errormessage : ''
							};
							self.forums = [];

							self.fetchAllForums = function() {
								console.log("fetchAllForums...")
								ForumService
										.fetchAllForums()
										.then(
												function(d) {
													self.forums = d;
												},
												function(errResponse) {
													console
															.error('Error while fetching Users');
												});
							};
							
							//self.fatchAllUsers();

							self.createForum = function(Forum) {
								console.log("createUser...")
								ForumService
										.createForum(Forum)
										.then(
												self.fetchAllUsers,
												function(errResponse) {
													console
															.error('Error while creating Forum.');
												});
							};
							
							
							self.submit = function() {
								{
									console.log('Saving New User', self.Forum);
									self.createForum(self.Forum);
								}
								self.reset();
								alert("Forum created successfully")
								$location.path('/');


							};

							self.reset = function() {
								self.Forum = {
										userid : '',
										id : '',
										title: '',
										description: '',
										status : '',
										reason : '',
										errorcode : '',
										errormessage : ''
					};
								$scope.myForm.$setPristine(); // reset Form
							};

							self.fetchAllForums();
						} ]);