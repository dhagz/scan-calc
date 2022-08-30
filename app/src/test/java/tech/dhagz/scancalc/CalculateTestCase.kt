package tech.dhagz.scancalc

import junit.framework.TestCase
import org.junit.Test
import tech.dhagz.scancalc.features.scan.ScanNumberNotFoundException
import tech.dhagz.scancalc.features.scan.ScanOperatorNotFoundException
import tech.dhagz.scancalc.features.scan.models.ScanOperationResult
import tech.dhagz.scancalc.features.scan.usecases.CalculateUseCase

/**
 * ...
 *
 *
 * Copyright (c) 2022 dhagz.tech. All rights reserved.
 *
 * @author Fernando "Dhagz" Dagoc Jr.
 * @since 2022-08-28 1:24 AM
 */
class CalculateTestCase {

    private val calculateUseCase = CalculateUseCase()

    @Test
    fun `success add`() {
        val res = calculateUseCase.invoke("1 + 2")

        TestCase.assertTrue(res is ScanOperationResult.Success)
        val a = res as ScanOperationResult.Success
        TestCase.assertEquals(1F, a.num1)
        TestCase.assertEquals(2F, a.num2)
        TestCase.assertEquals(3F, a.result)
    }

    @Test
    fun `success add float`() {
        val res = calculateUseCase.invoke("3.4 + 5")

        TestCase.assertTrue(res is ScanOperationResult.Success)
        val a = res as ScanOperationResult.Success
        TestCase.assertEquals(3.4F, a.num1)
        TestCase.assertEquals(5F, a.num2)
        TestCase.assertEquals(8.4F, a.result)
    }

    @Test
    fun `success subtract`() {
        val res = calculateUseCase.invoke("1 - 2")

        TestCase.assertTrue(res is ScanOperationResult.Success)
        val a = res as ScanOperationResult.Success
        TestCase.assertEquals(1F, a.num1)
        TestCase.assertEquals(2F, a.num2)
        TestCase.assertEquals(-1F, a.result)
    }

    @Test
    fun `success subtract float`() {
        val res = calculateUseCase.invoke("3 - 4.5")

        TestCase.assertTrue(res is ScanOperationResult.Success)
        val a = res as ScanOperationResult.Success
        TestCase.assertEquals(3F, a.num1)
        TestCase.assertEquals(4.5F, a.num2)
        TestCase.assertEquals(-1.5F, a.result)
    }

    @Test
    fun `success divide`() {
        val res = calculateUseCase.invoke("1 / 2")

        TestCase.assertTrue(res is ScanOperationResult.Success)
        val a = res as ScanOperationResult.Success
        TestCase.assertEquals(1F, a.num1)
        TestCase.assertEquals(2F, a.num2)
        TestCase.assertEquals(0.5F, a.result)
    }

    @Test
    fun `success multiply`() {
        val res = calculateUseCase.invoke("1 * 2")

        TestCase.assertTrue(res is ScanOperationResult.Success)
        val a = res as ScanOperationResult.Success
        TestCase.assertEquals(1F, a.num1)
        TestCase.assertEquals(2F, a.num2)
        TestCase.assertEquals(2F, a.result)
    }

    @Test
    fun `failed invalid number1`() {
        val res = calculateUseCase.invoke("1asdf * 2")

        TestCase.assertTrue(res is ScanOperationResult.Failed)
        val a = res as ScanOperationResult.Failed
        TestCase.assertTrue(a.throwable is ScanNumberNotFoundException)
    }

    @Test
    fun `failed invalid number2`() {
        val res = calculateUseCase.invoke("1 * 2asdf")

        TestCase.assertTrue(res is ScanOperationResult.Failed)
        val a = res as ScanOperationResult.Failed
        TestCase.assertTrue(a.throwable is ScanNumberNotFoundException)
    }

    @Test
    fun `failed empty expression`() {
        val res = calculateUseCase.invoke("")

        TestCase.assertTrue(res is ScanOperationResult.Failed)
        val a = res as ScanOperationResult.Failed
        TestCase.assertTrue(a.throwable is ScanOperatorNotFoundException)
    }

    @Test
    fun `failed expression not found`() {
        val res = calculateUseCase.invoke("+")

        TestCase.assertTrue(res is ScanOperationResult.Failed)
        val a = res as ScanOperationResult.Failed
        TestCase.assertTrue(a.throwable is ScanNumberNotFoundException)
    }

    @Test
    fun `failed operator not found`() {
        val res = calculateUseCase.invoke("1 = 2")

        TestCase.assertTrue(res is ScanOperationResult.Failed)
        val a = res as ScanOperationResult.Failed
        TestCase.assertTrue(a.throwable is ScanOperatorNotFoundException)
    }
}