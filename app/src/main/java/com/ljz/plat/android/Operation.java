package com.ljz.plat.android;

public interface Operation {
    double apply(double x, double y);
}

enum BaseOperation implements Operation {
    PLUS() {
        @Override
        public double apply(double x, double y) {
            return 0;
        }
    },
    MINUS() {
        @Override
        public double apply(double x, double y) {
            return 0;
        }
    };

    private void test() {
        BaseOperation.class.getEnumConstants();
    }
}
