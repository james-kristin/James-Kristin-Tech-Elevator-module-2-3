function initialize() {
    // Hook up the search box event handler
    document.getElementById("search-tb").addEventListener('keyup', displayProductList);

    // Hook up the event handler to close a message when clicked
    document.getElementById("message-bar").addEventListener('click', clearMessage);

    displayProductList();
}

/**
 * Returns a list of products, filtered if there's anything in the search box, else all products.
 */
function getProductList() {
    const searchValue = document.getElementById("search-tb").value;
    if (searchValue) {
        return productService.searchProducts(searchValue);
    } else {
        return productService.getProducts();
    }
}
function displayProductList() {
    // Get the list of products from the service
    const products = getProductList();

    // Get the section that will contain all the cards
    const cards = document.getElementById("product-cards");

    // Clear its current contents
    cards.innerHTML = "";

    // Loop through the list and build cards
    for (let product of products) {
        const cardElement = createCard(product);
        cards.appendChild(cardElement);
    }
}

/**
 *
 * @param {object} product A product from the database (service).
 */
function createCard(product) {

    // First the row
    const card = document.createElement("article");
    card.classList.add("product-card");

    // Then each data element on the card

    // Sku
    let div = document.createElement("div");
    div.classList.add("sku");
    div.innerText = product.productSku;
    card.appendChild(div);

    // price
    div = document.createElement("div");
    div.classList.add("price");
    div.innerText = currency(product.price);
    card.appendChild(div);

    // name
    div = document.createElement("div");
    div.innerText = product.name;
    div.classList.add("product-name");
    div.classList.add("action");
    div.setAttribute("data-id", product.productId);
    // Add event handler for name
    div.addEventListener('click', showDescription);
    card.appendChild(div);

    // image div
    div = document.createElement("div");
    div.classList.add("product-image");
    // image inside div
    let img = document.createElement("img");
    img.src = product.imageName;
    div.appendChild(img);
    card.appendChild(div);

    // Shopping cart icon
    div = document.createElement("div");
    div.classList.add("cart");
    div.innerHTML = '<i class="fa-solid fa-cart-plus icon action" title="Add item to cart"></i>';
    div.setAttribute("data-id", product.productId);
    // Add event handler for Shopping Cart
    div.addEventListener('click', addToCart);
    card.appendChild(div);

    return card;
}

function currency(value) {
    return new Intl.NumberFormat(`en-US`, {
        currency: `USD`,
        style: "currency",
    }).format(value);
}

// When the user clicks on an item, show the description in the message bar
function showDescription(ev) {
    // Get the id of the target
    const id = Number(ev.currentTarget.getAttribute("data-id"));
    // Get the product
    const product = productService.getProductById(id);
    // Add message
    if (product) {
        showMessage(product.description);
    }
}

function addToCart(ev) {
    // Get the id of the target
    const id = Number(ev.currentTarget.getAttribute("data-id"));

    // Add to cart here...

    // Add message
    showMessage(`Item ${id} added to cart!`);
}

function showMessage(msg) {
    const messageDiv = document.getElementById("message-bar");
    messageDiv.innerText = msg;
    messageDiv.style.visibility = "visible";
}

function clearMessage() {
    const messageDiv = document.getElementById("message-bar");
    messageDiv.innerText = "";
    messageDiv.style.visibility = "hidden";
}

// When this script loads, hook up to DOMContentLoaded
document.addEventListener('DOMContentLoaded', initialize);
