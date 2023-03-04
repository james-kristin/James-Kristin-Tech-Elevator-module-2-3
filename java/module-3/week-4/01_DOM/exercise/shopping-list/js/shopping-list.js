// add pageTitle
const pageTitle = 'My Shopping List'

// add groceries
const groceries = [
  { id: 1, item: 'PeanutButter', completed: false },
  { id: 2, item: 'Chicken', completed: false },
  { id: 3, item: 'Waffles', completed: false },
  { id: 4, item: 'Ice Cream', completed: false },
  { id: 5, item: 'Bread', completed: false },
  { id: 6, item: 'Salmon', completed: false },
  { id: 7, item: 'Cereal', completed: false },
  { id: 8, item: 'Water', completed: false },
  { id: 9, item: 'Butter', completed: false },
  { id: 10, item: 'Milk', completed: false }
]
/**
 * This function will get a reference to the title and set its text to the value
 * of the pageTitle variable that was set above.
 */
function setPageTitle() {
  const h1 = document.getElementById('title');
  h1.innerText = pageTitle;
}

/**
 * This function will loop over the array of groceries that was set above and add them to the DOM.
 */
function displayGroceries() {
  const groceriesList = document.getElementById('groceries');

  groceries.forEach(grocery => {
    const li = document.createElement('li')
    li.innerText = grocery.item
    
    groceriesList.appendChild(li);
  })

}

/**
 * This function will be called when the button is clicked. You will need to get a reference
 * to every list item and add the class completed to each one
 */
function markCompleted() {
  const allListItems = document.querySelectorAll('li')

  allListItems.forEach(item => {
    item.classList.add('completed')
  })
}

setPageTitle();

displayGroceries();

// Don't worry too much about what is going on here, we will cover this when we discuss events.
document.addEventListener('DOMContentLoaded', () => {
  // When the DOM Content has loaded attach a click listener to the button
  const button = document.querySelector('.btn');
  button.addEventListener('click', markCompleted);
});
