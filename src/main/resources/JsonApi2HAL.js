var list = JSON.parse(payload);
JSON.stringify(
    list.data.map( function(plant) {
        return {
            _id: plant.id,
            name: plant.attributes.name,
            description: plant.attributes.description,
            price: plant.attributes.price
        };
    })
);