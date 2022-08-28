package tech.dhagz.scancalc

import junit.framework.TestCase
import org.junit.Test
import tech.dhagz.scancalc.features.scan.usecases.CalculateUseCase
import tech.dhagz.scancalc.features.scan.ScanNumberNotFoundException
import tech.dhagz.scancalc.features.scan.models.ScanOperationResult

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
    fun `add success`() {
        val res = calculateUseCase.invoke("1 + 2")

        TestCase.assertTrue(res is ScanOperationResult.Success)
        val a = res as ScanOperationResult.Success
        TestCase.assertEquals(1F, a.num1)
        TestCase.assertEquals(2F, a.num2)
        TestCase.assertEquals(3F, a.result)
    }

    @Test
    fun `subtract success`() {
        val res = calculateUseCase.invoke("1 - 2")

        TestCase.assertTrue(res is ScanOperationResult.Success)
        val a = res as ScanOperationResult.Success
        TestCase.assertEquals(1F, a.num1)
        TestCase.assertEquals(2F, a.num2)
        TestCase.assertEquals(-1F, a.result)
    }

    @Test
    fun `divide success`() {
        val res = calculateUseCase.invoke("1 / 2")

        TestCase.assertTrue(res is ScanOperationResult.Success)
        val a = res as ScanOperationResult.Success
        TestCase.assertEquals(1F, a.num1)
        TestCase.assertEquals(2F, a.num2)
        TestCase.assertEquals(0.5F, a.result)
    }

    @Test
    fun `multiply success`() {
        val res = calculateUseCase.invoke("1 * 2")

        TestCase.assertTrue(res is ScanOperationResult.Success)
        val a = res as ScanOperationResult.Success
        TestCase.assertEquals(1F, a.num1)
        TestCase.assertEquals(2F, a.num2)
        TestCase.assertEquals(2F, a.result)
    }

    @Test
    fun `invalid number1`() {
        val res = calculateUseCase.invoke("1asdf * 2")

        TestCase.assertTrue(res is ScanOperationResult.Failed)
        val a = res as ScanOperationResult.Failed
        TestCase.assertTrue(a.throwable is ScanNumberNotFoundException)
    }

    @Test
    fun `invalid number2`() {
        val res = calculateUseCase.invoke("1 * 2asdf")

        TestCase.assertTrue(res is ScanOperationResult.Failed)
        val a = res as ScanOperationResult.Failed
        TestCase.assertTrue(a.throwable is ScanNumberNotFoundException)
    }
}