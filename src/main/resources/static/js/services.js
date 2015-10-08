function Resource($resource) {
	return $resource('/api/resource/:id', {id : '@id'}, {
		update: {method : 'PUT'}
	});
}
