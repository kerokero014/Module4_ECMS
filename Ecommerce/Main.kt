import kotlin.random.Random
import java.util.Scanner

// Data class for Product
data class Product(val id: Int, var name: String, var quantity: Int, var price: Double)

// Inventory management class
class Inventory {
    private val products = mutableListOf<Product>()

    // Function to add a product to the inventory
    fun addProduct(product: Product) {
        products.add(product)
        println("Product added: $product")
    }

    // Function to update product quantity
    fun updateProductQuantity(id: Int, quantity: Int) {
        val product = products.find { it.id == id }
        product?.let {
            it.quantity = quantity
            println("Product updated: $it")
        } ?: println("Product not found")
    }

    // Function to display all products
    fun displayProducts() {
        println("All Products in Inventory:")
        products.forEach { println(it) }
    }

    // Function to search for a product by name
    fun searchProductByName(name: String): Product? {
        return products.find { it.name == name }
    }

    // Function to remove a product by ID
    fun removeProductById(id: Int) {
        val iterator = products.iterator()
        while (iterator.hasNext()) {
            val product = iterator.next()
            if (product.id == id) {
                iterator.remove()
                println("Product removed: $product")
                return
            }
        }
        println("Product not found")
    }

    // Function to get the total value of the inventory
    fun getTotalInventoryValue(): Double {
        return products.sumOf { it.quantity * it.price }
    }
}

// Shopping cart management class
class ShoppingCart {
    private val cartItems = mutableListOf<Product>()

    // Function to add a product to the cart
    fun addToCart(product: Product, quantity: Int) {
        val cartItem = product.copy(quantity = quantity)
        cartItems.add(cartItem)
        println("Added to cart: $cartItem")
    }

    // Function to display cart items
    fun displayCartItems() {
        println("Items in Cart:")
        cartItems.forEach { println(it) }
    }

    // Function to get the total cart value
    fun getTotalCartValue(): Double {
        return cartItems.sumOf { it.quantity * it.price }
    }
}

// Main function to interact with the user
fun main() {
    val inventory = Inventory()
    val shoppingCart = ShoppingCart()
    val scanner = Scanner(System.`in`)

    while (true) {
        println("""
            1. Add Product to Inventory
            2. Update Product Quantity
            3. Display All Products
            4. Search Product by Name
            5. Remove Product by ID
            6. Add Product to Cart
            7. Display Cart Items
            8. Get Total Inventory Value
            9. Get Total Cart Value
            0. Exit
        """.trimIndent())

        print("Enter your choice: ")
        when (scanner.nextInt()) {
            1 -> {
                print("Enter product name: ")
                val name = scanner.next()
                print("Enter product quantity: ")
                val quantity = scanner.nextInt()
                print("Enter product price: ")
                val price = scanner.nextDouble()
                val id = Random.nextInt(1000, 9999)
                val product = Product(id, name, quantity, price)
                inventory.addProduct(product)
            }
            2 -> {
                print("Enter product ID: ")
                val id = scanner.nextInt()
                print("Enter new quantity: ")
                val quantity = scanner.nextInt()
                inventory.updateProductQuantity(id, quantity)
            }
            3 -> inventory.displayProducts()
            4 -> {
                print("Enter product name: ")
                val name = scanner.next()
                val product = inventory.searchProductByName(name)
                if (product != null) {
                    println("Product found: $product")
                } else {
                    println("Product not found")
                }
            }
            5 -> {
                print("Enter product ID: ")
                val id = scanner.nextInt()
                inventory.removeProductById(id)
            }
            6 -> {
                print("Enter product ID: ")
                val id = scanner.nextInt()
                print("Enter quantity to add to cart: ")
                val quantity = scanner.nextInt()
                val product = inventory.searchProductByName(id.toString())
                if (product != null && product.quantity >= quantity) {
                    shoppingCart.addToCart(product, quantity)
                    inventory.updateProductQuantity(id, product.quantity - quantity)
                } else {
                    println("Product not found or insufficient quantity")
                }
            }
            7 -> shoppingCart.displayCartItems()
            8 -> {
                val totalValue = inventory.getTotalInventoryValue()
                println("Total Inventory Value: $$totalValue")
            }
            9 -> {
                val totalValue = shoppingCart.getTotalCartValue()
                println("Total Cart Value: $$totalValue")
            }
            0 -> {
                println("Exiting...")
                return
            }
            else -> println("Invalid choice, please try again")
        }
    }
}
