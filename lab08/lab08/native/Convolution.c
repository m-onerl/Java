#include <jni.h>
#include "Convolution.h"

JNIEXPORT jobjectArray JNICALL Java_Convolution_nativeConvolution(JNIEnv *env, jobject obj, jobjectArray matrixArray, jobjectArray kernelArray) {
    jsize matrixRows = (*env)->GetArrayLength(env, matrixArray);
    jsize matrixCols = (*env)->GetArrayLength(env, (*env)->GetObjectArrayElement(env, matrixArray, 0));
    jsize kernelRows = (*env)->GetArrayLength(env, kernelArray);
    jsize kernelCols = (*env)->GetArrayLength(env, (*env)->GetObjectArrayElement(env, kernelArray, 0));

    jobjectArray resultArray = (*env)->NewObjectArray(env, matrixRows, (*env)->FindClass(env, "[D"), NULL);

    for (int i = 0; i < matrixRows; i++) {
        jdoubleArray resultRow = (*env)->NewDoubleArray(env, matrixCols);
        jdouble *resultRowElements = (*env)->GetDoubleArrayElements(env, resultRow, NULL);

        for (int j = 0; j < matrixCols; j++) {
            double sum = 0;

            for (int ki = 0; ki < kernelRows; ki++) {
                int mi = i + ki - kernelRows / 2;
                if (mi < 0 || mi >= matrixRows) continue;

                jdoubleArray matrixRow = (*env)->GetObjectArrayElement(env, matrixArray, mi);
                jdouble *matrixRowElements = (*env)->GetDoubleArrayElements(env, matrixRow, NULL);

                jdoubleArray kernelRow = (*env)->GetObjectArrayElement(env, kernelArray, ki);
                jdouble *kernelRowElements = (*env)->GetDoubleArrayElements(env, kernelRow, NULL);

                for (int kj = 0; kj < kernelCols; kj++) {
                    int mj = j + kj - kernelCols / 2;
                    if (mj < 0 || mj >= matrixCols) continue;

                    sum += matrixRowElements[mj] * kernelRowElements[kj];
                }

                (*env)->ReleaseDoubleArrayElements(env, matrixRow, matrixRowElements, JNI_ABORT);
                (*env)->ReleaseDoubleArrayElements(env, kernelRow, kernelRowElements, JNI_ABORT);
            }

            resultRowElements[j] = sum;
        }

        (*env)->SetDoubleArrayRegion(env, resultRow, 0, matrixCols, resultRowElements);
        (*env)->SetObjectArrayElement(env, resultArray, i, resultRow);
        (*env)->ReleaseDoubleArrayElements(env, resultRow, resultRowElements, 0);
    }

    return resultArray;
}
