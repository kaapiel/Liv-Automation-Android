LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)


LOCAL_SDK_VERSION := 9
LOCAL_NDK_STL_VARIANT := gnustl_static
LOCAL_MODULE    := Criptohelper
LOCAL_SRC_FILES := Criptohelper.c sha256.c
LOCAL_CPP_FEATURES := rtti
LOCAL_CFLAGS += $(local_cflags) -DPIC -fPIC -frtti
LOCAL_LDLIBS := $(LOCAL_LDLIBS) -llog $(call host-path,$(NDK_ROOT)/sources/cxx-stl/gnu-libstdc++/$(TOOLCHAIN_VERSION)/libs/$(TARGET_ARCH_ABI)/libsupc++$(TARGET_LIB_EXTENSION))
include $(BUILD_SHARED_LIBRARY)