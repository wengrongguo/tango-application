package org.tango.utils.enums;

/**
 * User: tAngo
 * Date: 12-12-23
 * Time: 上午4:28
 */
public enum EncryptedType {
    MD5("MD5"),
    SHA_1("SHA-1");

    private String value;

    private EncryptedType(String en) {
        this.value = en;
    }

    @Override
    public String toString() {
        return this.value;
    }
}