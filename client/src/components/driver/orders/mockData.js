const mockOrder = [
    {
        key: 1,
        orderedAt:"05-12-2021",
        user : {
          firstName: "Terbinos",
          lastName: "Chekol",
          email: "terbinos@gmail.com",
          address: {
            "city":"fairfield",
            "state":"Iowa",
            "streetNumber":"1000 N 4th st",
            "zip":"52557"
          }
        },
        menus: [
            {
                key: 1,
                name: "New york Steak",
                category: "Dinner",
                price: 39.99,
                description:
                "The New York Steak is also known as New York strip steak, ambassador steak, strip loin steak, club steak, or the Omaha Strip. But it's most ...",
                quantity:2
            },
            {
                key: 3,
                name: "Special Pizza",
                price: 29.99,
                category: "Lunch",
                description:
                  "Our Special Pizza is the perfect homemade recipe with a crisp and chewy crust, tangy tomato sauce, and delicious toppings",
                quantity:1
              
            },
        ],
        status: "PICKED" 
      },
      {
        key: 2,
        orderedAt:"15-11-2021",
        user : {
          firstName: "Mathew",
          lastName: "Johnson",
          email: "terbinos@gmail.com",
          address: {
            "city":"fairfield",
            "state":"Iowa",
            "streetNumber":"1000 N 4th st",
            "zip":"52557"
          }
        },
        menus: [
            {
                key: 1,
                name: "New york Steak",
                category: "Dinner",
                price: 39.99,
                description:
                "The New York Steak is also known as New York strip steak, ambassador steak, strip loin steak, club steak, or the Omaha Strip. But it's most ...",
                quantity:3
            }, 
            {
                key: 2,
                name: "Double Cheese Burger",
                price: 11.99,
                category: "Lunch",
                description:
                  "The McDonald's Double Cheeseburger features two 100% pure beef burger patties seasoned with just a pinch of salt and pepper.",
                quantity:1
            },
        ],
        status: "DELIVERED" 
      },
      {
        key: 3,
        orderedAt:"06-10-2021",
        user : {
          firstName: "Ben",
          lastName: "Foster",
          email: "terbinos@gmail.com",
          address: {
            "city":"fairfield",
            "state":"Iowa",
            "streetNumber":"1000 N 4th st",
            "zip":"52557"
          }
        },
        menus: [
            {
                key: 4,
                name: "Ethiopian Dish",
                price: 18.99,
                category: "Lunch",
                description:
                  "A typical dish consists of injera accompanied by a spicy stew, which frequently includes beef, lamb, vegetables and various types of legumes, such...",
                quantity:1
            },
            {
                key: 3,
                name: "Special Pizza",
                price: 29.99,
                category: "Lunch",
                description:
                  "Our Special Pizza is the perfect homemade recipe with a crisp and chewy crust, tangy tomato sauce, and delicious toppings",
                quantity:1
              
              },
        ],
        status: "ACCEPTED" 
      },
      {
        key: 4,
        orderedAt:"01-08-2021",
        user : {
          firstName: "Ronald",
          lastName: "Fredrick",
          email: "terbinos@gmail.com",
          address: {
            "city":"fairfield",
            "state":"Iowa",
            "streetNumber":"1000 N 4th st",
            "zip":"52556"
          }
        },
        menus: [
            {
                key: 4,
                name: "Ethiopian Dish",
                price: 18.99,
                category: "Lunch",
                description:
                  "A typical dish consists of injera accompanied by a spicy stew, which frequently includes beef, lamb, vegetables and various types of legumes, such...",
                quantity:1
              }, 
            {
                key: 2,
                name: "Double Cheese Burger",
                price: 11.99,
                category: "Lunch",
                description:
                  "The McDonald's Double Cheeseburger features two 100% pure beef burger patties seasoned with just a pinch of salt and pepper.",
                quantity:4
            },
        ],
        status: "READY"
      },
      {
        key: 5,
        orderedAt:"01-08-2021",
        user : {
          firstName: "Ronald",
          lastName: "Fredrick",
          email: "terbinos@gmail.com",
          address: {
            "city":"fairfield",
            "state":"Iowa",
            "streetNumber":"1000 N 4th st",
            "zip":"52556"
          }
        },
        menus: [
            {
                key: 4,
                name: "Ethiopian Dish",
                price: 18.99,
                category: "Lunch",
                description:
                  "A typical dish consists of injera accompanied by a spicy stew, which frequently includes beef, lamb, vegetables and various types of legumes, such...",
                quantity:1
              }, 
            {
                key: 2,
                name: "Double Cheese Burger",
                price: 11.99,
                category: "Lunch",
                description:
                  "The McDonald's Double Cheeseburger features two 100% pure beef burger patties seasoned with just a pinch of salt and pepper.",
                quantity:4
            },
            {
              key: 3,
              name: "Special Pizza",
              price: 29.99,
              category: "Lunch",
              description:
                "Our Special Pizza is the perfect homemade recipe with a crisp and chewy crust, tangy tomato sauce, and delicious toppings",
              quantity:1
            
            },
        ],
        status: "PAID"
      }
]

export default mockOrder;