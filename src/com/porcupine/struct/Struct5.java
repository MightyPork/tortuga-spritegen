package com.porcupine.struct;


import com.porcupine.math.Calc;


/**
 * Structure of 5 objects.
 * 
 * @author MightyPork
 * @copy (c) 2012
 * @param <T1> 1st object class
 * @param <T2> 2nd object class
 * @param <T3> 3rd object class
 * @param <T4> 4th object class
 * @param <T5> 5th object class
 */
public class Struct5<T1, T2, T3, T4, T5> {
	/**
	 * 1st object
	 */
	public T1 a;

	/**
	 * 2nd object
	 */
	public T2 b;

	/**
	 * 3rd object
	 */
	public T3 c;

	/**
	 * 4th object
	 */
	public T4 d;

	/**
	 * 5th object
	 */
	public T5 e;

	/**
	 * Make structure of 4 objects
	 * 
	 * @param objA 1st object
	 * @param objB 2nd object
	 * @param objC 3rd object
	 * @param objD 4th object
	 * @param objE 5th object
	 */
	public Struct5(T1 objA, T2 objB, T3 objC, T4 objD, T5 objE) {
		a = objA;
		b = objB;
		c = objC;
		d = objD;
		e = objE;
	}

	/**
	 * @return 1st object
	 */
	public T1 getA() {
		return a;
	}

	/**
	 * @return 2nd object
	 */
	public T2 getB() {
		return b;
	}

	/**
	 * @return 3rd object
	 */
	public T3 getC() {
		return c;
	}

	/**
	 * @return 4th object
	 */
	public T4 getD() {
		return d;
	}

	/**
	 * @return 5th object
	 */
	public T5 getE() {
		return e;
	}

	/**
	 * @return 1st object
	 */
	public T1 get1() {
		return a;
	}

	/**
	 * @return 2nd object
	 */
	public T2 get2() {
		return b;
	}

	/**
	 * @return 3rd object
	 */
	public T3 get3() {
		return c;
	}

	/**
	 * @return 4th object
	 */
	public T4 get4() {
		return d;
	}

	/**
	 * @return 5th object
	 */
	public T5 get5() {
		return e;
	}



	/**
	 * Set 1st object
	 * 
	 * @param obj 1st object
	 */
	public void setA(T1 obj) {
		a = obj;
	}

	/**
	 * Set 2nd object
	 * 
	 * @param obj 2nd object
	 */
	public void setB(T2 obj) {
		b = obj;
	}

	/**
	 * Set 3rd object
	 * 
	 * @param obj 3rd object
	 */
	public void setC(T3 obj) {
		c = obj;
	}

	/**
	 * Set 4th object
	 * 
	 * @param obj 4th object
	 */
	public void setD(T4 obj) {
		d = obj;
	}

	/**
	 * Set 5th object
	 * 
	 * @param obj 5th object
	 */
	public void setE(T5 obj) {
		e = obj;
	}

	/**
	 * Set 1st object
	 * 
	 * @param obj 1st object
	 */
	public void set1(T1 obj) {
		a = obj;
	}

	/**
	 * Set 2nd object
	 * 
	 * @param obj 2nd object
	 */
	public void set2(T2 obj) {
		b = obj;
	}

	/**
	 * Set 3rd object
	 * 
	 * @param obj 3rd object
	 */
	public void set3(T3 obj) {
		c = obj;
	}

	/**
	 * Set 4th object
	 * 
	 * @param obj 4th object
	 */
	public void set4(T4 obj) {
		d = obj;
	}

	/**
	 * Set 5th object
	 * 
	 * @param obj 5th object
	 */
	public void set5(T5 obj) {
		e = obj;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!this.getClass().equals(obj.getClass())) {
			return false;
		}

		Struct5<?, ?, ?, ?, ?> t = (Struct5<?, ?, ?, ?, ?>) obj;

		return Calc.areObjectsEqual(a, t.a) && Calc.areObjectsEqual(b, t.b) && Calc.areObjectsEqual(c, t.c) && Calc.areObjectsEqual(d, t.d)
				&& Calc.areObjectsEqual(e, t.e);

	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (a == null ? 0 : a.hashCode());
		hash += (b == null ? 0 : b.hashCode());
		hash += (c == null ? 0 : c.hashCode());
		hash += (d == null ? 0 : d.hashCode());
		hash += (e == null ? 0 : e.hashCode());
		return hash;
	}

	@Override
	public String toString() {
		return "STRUCT {" + a + "," + b + "," + c + "," + d + "," + e + "}";
	}

}
