# Android Beer Festival Client

This is a simple app to integrate with the new CAMRA Drink & Brewer Database web app.

For the first release it will assume that there is only one festival (Worcester)

## Application Flow

1. On startup the drink and brewer databases are loaded from their sources in the main apk.
1.* This is done whilst viewing a splash screen
1. The user then is shown a page to:
1.1. Show all Drinks (by type?)
1.1. Show all Brewers
1.1. Search Drinks (by type?)
1.1. Search Brewers

## Search Result Pages

1. The search results are a simple list view of the results. When a result is selected it shows the detail view
1.1. The drink list view should show the name, brewer and remaining status

## Detail Views

### Drink Details
This displays all the main details of the drink (name, description, abv, price, remaining status and link to brewer).

#### Secondary Items
The ability to rate (in the web app) and to add a drink to a wishlist (internal to app)

### Brewer Details
Shows name, description, location, (optional) link to website and a list of all drinks by brewer at the current festival
(or maybe indicate which ones are at the current festival). Drink entries are links to the drink details.
