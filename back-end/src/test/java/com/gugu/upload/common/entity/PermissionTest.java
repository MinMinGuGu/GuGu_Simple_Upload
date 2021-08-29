package com.gugu.upload.common.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * The type Permission test.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
class PermissionTest {

    /**
     * Test equals.
     */
    @Test
    void testEquals() {
        Permission permission = new Permission();
        permission.setId(1).setName("minmin");
        int identityHashCode = System.identityHashCode(permission);
        Permission permission1 = new Permission();
        permission1.setId(2).setName("minmin");
        int identityHashCode1 = System.identityHashCode(permission1);
        assertNotEquals(identityHashCode, identityHashCode1);
        assertEquals(permission.hashCode(), permission1.hashCode());
        assertEquals(permission, permission1);
    }
}