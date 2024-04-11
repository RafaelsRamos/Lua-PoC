require 'navigation'
require 'gui'

function navigate_to_fragment_success(message)
    navigation.navigate_to_success(message)
end

function invoked_service_navigation(label)
    print("Invoked service navigation: " .. label)
    if label == "ServiceA" then
        navigation.navigate("FragmentA")
    elseif label == "ServiceB" then
        navigation.navigate("FragmentB")
    elseif label == "ServiceC" then
        navigation.navigate("FragmentC")
    elseif label == "Success" then
        navigation.navigate("SuccessFragment")
    elseif label == "Back" then
        navigation.navigate("Back")
    end
end

function invoke_service_a(success, amount)
    if success == true then
        navigate_to_fragment_success("Success for "..amount.."€")
    else
        gui.show_toast("Call to service A failed for "..amount.."€")
    end
end