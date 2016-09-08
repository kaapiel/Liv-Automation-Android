//
// Created by Vilmar Bispo Filho on 1/21/16.
//
#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include "sha256.h"
#include "br_com_pontomobi_livelopontos_ui_rescuecode_CriptoHelper.h"


void sha256(char **text);
static jstring getPackageName(JNIEnv *env, jobject context);

JNIEXPORT jstring JNICALL Java_br_com_pontomobi_livelopontos_ui_rescuecode_CriptoHelper_getSeed(JNIEnv *env, jobject thisObj, jobject context, jstring doc, jstring code){

    const char *strDoc = (*env)->GetStringUTFChars(env, doc, 0);
    const char *srtCode = (*env)->GetStringUTFChars(env, code, 0);
    const char *packageName = (*env)->GetStringUTFChars(env, getPackageName(env, context), 0);

    char *result = (char *) malloc(strlen(strDoc) + strlen(srtCode) + strlen(packageName) + 1);

    strcpy(result, strDoc);
    strcat(result, srtCode);
    strcat(result, packageName);

    sha256(&result);

    return (*env)->NewStringUTF(env, result);
}

void sha256(char **text) {
    unsigned char hash[32];

    SHA256_CTX ctx;

    sha256_init(&ctx);
    sha256_update(&ctx, *text, strlen(*text));
    sha256_final(&ctx, hash);

    char *result = (char *) malloc(sizeof(char) * (64 + 1));

    unsigned int i;

    for (i = 0; i < 32; i++) {
        sprintf(result + i * 2, "%02x", hash[i]);
    }

    result[64] = '\0';

    free(*text);
    *text = result;
}

static jstring getPackageName(JNIEnv *env, jobject context) {
    jclass contextClass = (*env)->GetObjectClass(env, context);
    jmethodID packageNameMID = (*env)->GetMethodID(env, contextClass, "getPackageName", "()Ljava/lang/String;");

    return (*env)->CallObjectMethod(env, context, packageNameMID);
}