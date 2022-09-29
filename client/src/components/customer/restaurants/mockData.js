const mockRestaurant = {
    id: 1,
    name: "Restaurant",
    address:{
        city: "fairfield",
        state: "Iowa",
        streetNumber: "1000 N 4th st",
        zip: "52557"
    },
    menus: [
        {
            id: 1,
            name: "New york Steak",
            category: "Dinner",
            price: 39.99,
            description:
            "The New York Steak is also known as New York strip steak, ambassador steak, strip loin steak, club steak, or the Omaha Strip. But it's most ...",
            imageUrl: "https://s3-media0.fl.yelpcdn.com/bphoto/qecCb0ZJvCQouPhIUSQL_w/o.jpg"
        },
        {
            id: 2,
            name: "Double Cheese Burger",
            price: 11.99,
            category: "Lunch",
            description:
              "The McDonald's Double Cheeseburger features two 100% pure beef burger patties seasoned with just a pinch of salt and pepper.",
            imageUrl: "https://s3-media0.fl.yelpcdn.com/bphoto/qecCb0ZJvCQouPhIUSQL_w/o.jpg"

        },
        {
            id: 3,
            name: "Special Pizza",
            price: 29.99,
            category: "Lunch",
            description:
                "Our Special Pizza is the perfect homemade recipe with a crisp and chewy crust, tangy tomato sauce, and delicious toppings",
            imageUrl: "https://s3-media0.fl.yelpcdn.com/bphoto/qecCb0ZJvCQouPhIUSQL_w/o.jpg"

        },
        {
            id: 4,
            name: "Ethiopian Dish",
            price: 18.99,
            category: "Lunch",
            description:
              "A typical dish consists of injera accompanied by a spicy stew, which frequently includes beef, lamb, vegetables and various types of legumes, such...",
            imageUrl: "https://s3-media0.fl.yelpcdn.com/bphoto/qecCb0ZJvCQouPhIUSQL_w/o.jpg"

          }, 
    ]
}
      

export default mockRestaurant;