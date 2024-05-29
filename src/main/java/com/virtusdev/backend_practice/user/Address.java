package com.virtusdev.backend_practice.user;

public record Address(
        String street,
        String suite,
        String city,
        String zipCode,
        Geo geo
) {
}
