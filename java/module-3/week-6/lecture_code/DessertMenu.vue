// The select options should be each category name
// When that category is chosen, the items in that category are displayed with checkboxes
// When an item is checked, it is added to the order
// the order with all the items selected, updates as each item is selected
// the order should only appear if at least one item is chosen

<template>
  <div>
    <div>
      <select>
        <option>
        </option>
      </select>
    </div>
    <div>
      <input />
      <label></label>
    </div>
    <div>
      <!-- Display order here -->
      Your order:
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      dessertCategories: [
        {
          name: "Cakes",
          items: ["Chocolate Cake", "Angel Food Cake", "Strawberry Shortcake"],
        },
        {
          name: "Pies",
          items: [
            "Apple Pie",
            "Cherry Cobbler",
            "Mississippi Mud Pie",
            "Key Lime Pie",
          ],
        },
        {
          name: "Cookies",
          items: ["Chocolate Chip Cookies", "Snickerdoodles", "Oreos"],
        },
      ],
    };
  },
  computed: {

  },
};
</script>

<style>
.checkboxes {
  display: inline-block;
  margin: 10px;
}
</style>
