// Handle DOM content loaded event
document.addEventListener('DOMContentLoaded', () => {
    console.log('Auction platform script loaded');
    addActiveNavLink();
    loadProducts(); // Load the products when the page is loaded
});

// Add active class to the current page's nav link
function addActiveNavLink() {
    const currentPath = window.location.pathname;
    const navLinks = document.querySelectorAll('nav a');

    navLinks.forEach(link => {
        if (link.href.includes(currentPath)) {
            link.classList.add('active');
        }
    });
}

// Function to display bid options when a product is clicked
function showBidOption(productName) {
    const products = document.querySelectorAll('.product');
    products.forEach(product => product.classList.remove('active'));
    
    const productElement = document.querySelector(`.product[onclick="showBidOption('${productName}')"]`);
    productElement.classList.add('active');
}

// Function to handle placing a bid
function placeBid(productName) {
    const bidInput = document.getElementById(`bid-price-${productName.toLowerCase().replace(/\s+/g, '')}`);
    const bidAmount = parseFloat(bidInput.value);

    if (isNaN(bidAmount) || bidAmount <= 0) {
        alert("Please enter a valid bid amount.");
        return;
    }

    alert(`You placed a bid of $${bidAmount} on ${productName}.`);
    saveOrder(productName, 'Bid', bidAmount);
}

// Function to handle placing an order
function placeOrder(productName) {
    alert(`Order placed for ${productName}`);
    saveOrder(productName, 'Order');
}

// Function to save the order (bid or purchase) - Simulated for demonstration
function saveOrder(productName, type, bidAmount = 0) {
    const order = {
        productName: productName,
        type: type,
        bidAmount: bidAmount,
        date: new Date().toLocaleDateString()
    };

    console.log('Order saved:', order);
    displayOrderInModal(order);
}

// Function to display the order details in a modal
function displayOrderInModal(order) {
    const modal = document.getElementById("orderModal");
    const modalContent = document.querySelector(".modal-content");

    modal.style.display = "block";
    modalContent.innerHTML = `
        <span class="close">&times;</span>
        <h2>Order Details</h2>
        <p><strong>Product:</strong> ${order.productName}</p>
        <p><strong>Type:</strong> ${order.type}</p>
        <p><strong>Date:</strong> ${order.date}</p>
        ${order.bidAmount > 0 ? `<p><strong>Bid Amount:</strong> $${order.bidAmount}</p>` : ''}
        <button onclick="closeModal()">Close</button>
    `;

    // Close modal when the user clicks the close button
    const closeModalButton = document.querySelector('.close');
    closeModalButton.onclick = closeModal;
    
    // Close modal when clicking anywhere outside of it
    window.onclick = function(event) {
        if (event.target === modal) {
            closeModal();
        }
    };
}

// Close modal function
function closeModal() {
    const modal = document.getElementById("orderModal");
    modal.style.display = "none";
}

// Form validation for registration
function validateRegistrationForm(event) {
    const username = document.getElementById('username').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    if (!username || !email || !password) {
        alert("All fields are required.");
        event.preventDefault();
        return false;
    }

    if (!validateEmail(email)) {
        alert("Please enter a valid email address.");
        event.preventDefault();
        return false;
    }

    return true;
}

// Email validation function
function validateEmail(email) {
    const regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
    return regex.test(email);
}

// Attach validation to registration form submit
const registerForm = document.getElementById('registerForm');
if (registerForm) {
    registerForm.addEventListener('submit', validateRegistrationForm);
}

// Form validation for login
function validateLoginForm(event) {
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    if (!email || !password) {
        alert("Please enter both email and password.");
        event.preventDefault();
        return false;
    }

    if (!validateEmail(email)) {
        alert("Please enter a valid email address.");
        event.preventDefault();
        return false;
    }

    return true;
}

// Attach validation to login form submit
const loginForm = document.getElementById('loginForm');
if (loginForm) {
    loginForm.addEventListener('submit', validateLoginForm);
}

// For handling order actions - update order details in the page dynamically
function handleOrderAction(orderAction, productName) {
    const orderSection = document.getElementById("orders");
    
    // Creating order item dynamically
    const orderItem = document.createElement("div");
    orderItem.classList.add("order");
    orderItem.innerHTML = `
        <p><strong>Product:</strong> ${productName}</p>
        <p><strong>Action:</strong> ${orderAction}</p>
        <button onclick="viewOrderDetails('${productName}', '${orderAction}')">View Details</button>
    `;
    
    // Append to order list
    orderSection.appendChild(orderItem);
}

// View details of an order in a modal
function viewOrderDetails(productName, orderAction) {
    alert(`Viewing details for ${orderAction} on ${productName}`);
    // Additional order details could be shown dynamically in a modal here
}

// Show product list dynamically (mock data for demonstration)


    const productGrid = document.querySelector('.product-grid');
    products.forEach(product => {
        const productElement = document.createElement('div');
        productElement.classList.add('product');
        productElement.setAttribute('onclick', `showBidOption('${product.name}')`);
        productElement.innerHTML = `
            <img src="${product.img}" alt="${product.name}">
            <h3>${product.name}</h3>
            <div class="bid-option" id="bid-option-${product.name.toLowerCase().replace(/\s+/g, '')}">
                <label for="bid-price-${product.name.toLowerCase().replace(/\s+/g, '')}">Enter your bid:</label>
                <input type="number" id="bid-price-${product.name.toLowerCase().replace(/\s+/g, '')}" value="${product.initialPrice}" min="0">
                <button onclick="placeBid('${product.name}')">Place Bid</button>
                <button onclick="placeOrder('${product.name}')">Place Order</button>
            </div>
        `;
        productGrid.appendChild(productElement);
    });


// Call loadProducts when page is ready
window.onload = loadProducts;
