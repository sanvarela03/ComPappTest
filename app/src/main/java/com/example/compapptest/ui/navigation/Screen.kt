package com.example.compapptest.ui.navigation

sealed class Screen(val route: String) {
    object SignUpScreen : Screen("sign_up_screen")
    object TermsAndConditionsScreen : Screen("terms_and_conditions_screen")
    object SignInScreen : Screen("sign_in_screen")
    object HomeScreen : Screen("home_screen")
    object AddEditCustomerScreen : Screen("add_edit_customer_screen")
    object ProducerSearchScreen : Screen("producer_search_screen")
    object HomeDrawerScreen : Screen("home_drawer_screen")
    object SplashScreen : Screen("splash_screen")
    object AddressListScreen : Screen("address_list_screen")
    object OrderListScreen : Screen("order_list_screen")
    object AddEditAddressScreen : Screen("add_edit_address_screen")
    object ProducerDetailScreen : Screen("producer_detail_screen")
    object ShoppingCartScreen : Screen("shopping_cart_screen")
    object SendOrderScreen : Screen("send_order_screen")
    object PendingOrdersScreen : Screen("pending_orders_screen")
}