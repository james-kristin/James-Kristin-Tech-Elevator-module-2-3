/*
    app.js

*/



function addProductCard() {
    const productCardsSection = document.getElementById('product-cards');

    let productCards = productService.getProducts(7);
    productArray = Array.from(productCards);

    for (product of productArray) {
        const article = document.createElement('article');
        article.className = 'product-card';

        const sku = document.createElement('div');
        sku.className = 'sku';
        sku.innerText = product.productSku;

        const price = document.createElement('div');
        price.className = 'price';
        productPrice = product.price;
        usd = Intl.NumberFormat(`en-US`, {
            currency: `USD`,
            style: "currency",
        }).format(productPrice);
        price.innerText = usd;

        const productName = document.createElement('div');
        productName.className = 'product-name action';
        productName.setAttribute('data-id', product.productId);
        productName.innerText = product.name;
        productName.addEventListener('click', (event) => {
            const id = Number(event.currentTarget.getAttribute("data-id"));
            const product = productService.getProductById(id);
            alert(product.description);
        });
        

        const productImage = document.createElement('div');
        productImage.className = 'product-image';

        const img = document.createElement('img');
        img.src = product.imageName;

        const cart = document.createElement('div');
        cart.className = 'cart';

        const cartIcon = document.createElement('i');
        cartIcon.className = 'fa-solid fa-cart-plus icon action';
        cartIcon.setAttribute('data-id', product.productId);
        cartIcon.title = 'Add item to cart';
        cartIcon.addEventListener('click', (event) => {
            const id = Number(event.currentTarget.getAttribute("data-id"));
            const product = productService.getProductById(id);
            alert(product.name + " added to cart!");
        });

        

        productCardsSection.appendChild(article);
        article.appendChild(sku);
        article.appendChild(price);
        article.appendChild(productName);
        article.appendChild(productImage);
        productImage.appendChild(img);
        article.appendChild(cart);
        cart.appendChild(cartIcon);
        
    };

    

    
    
};

function searchProducts(event) {
    const value = event.target.value;
    
}


document.addEventListener("DOMContentLoaded", () => {
    
    addProductCard();

    const searchTerm = document.getElementById('search');

    searchTerm.addEventListener('keyup', searchProducts(event));
    
    

}); 