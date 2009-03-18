package com.zzh.lang;

import java.util.Calendar;

import com.zzh.Nutz;
import com.zzh.ioc.FailToMakeObjectException;

import junit.framework.TestCase;

public class MirrorTest extends TestCase {

	abstract class A<T> {}

	abstract class B<X, Y> {}

	public void testOneParam() {
		A<String> a = new A<String>() {};
		assertEquals(String.class, Mirror.getTypeParams(a.getClass())[0]);

	}

	public void testTwoParam() {
		B<Integer, String> b = new B<Integer, String>() {};
		assertEquals(Integer.class, Mirror.getTypeParams(b.getClass())[0]);
		assertEquals(String.class, Mirror.getTypeParams(b.getClass())[1]);
	}

	public void testWrapper() {
		assertTrue(Mirror.me(Integer.class).isWrpperOf(int.class));
		assertFalse(Mirror.me(Integer.class).isWrpperOf(float.class));
		assertTrue(Mirror.me(Float.class).isWrpperOf(float.class));
	}

	public void testCanCastToDirectly() {
		assertTrue(Mirror.me(Integer.class).canCastToDirectly(int.class));
		assertTrue(Mirror.me(int.class).canCastToDirectly(Integer.class));
		assertTrue(Mirror.me(String.class).canCastToDirectly(CharSequence.class));
		assertTrue(Mirror.me(String.class).canCastToDirectly(String.class));
		assertTrue(Mirror.me(Boolean.class).canCastToDirectly(boolean.class));
		assertTrue(Mirror.me(boolean.class).canCastToDirectly(Boolean.class));
		assertTrue(Mirror.me(int.class).canCastToDirectly(short.class));

		assertFalse(Mirror.me(int.class).canCastToDirectly(Short.class));
		assertFalse(Mirror.me(CharSequence.class).canCastToDirectly(String.class));
		assertFalse(Mirror.me(String.class).canCastToDirectly(StringBuilder.class));
		assertFalse(Mirror.me(String.class).canCastToDirectly(StringBuilder.class));
		assertFalse(Mirror.me(boolean.class).canCastToDirectly(float.class));
		assertFalse(Mirror.me(boolean.class).canCastToDirectly(short.class));

		assertTrue(Mirror.me(FailToMakeObjectException.class).canCastToDirectly(Throwable.class));

		assertTrue(Mirror.me(Character.class).canCastToDirectly(char.class));
		assertTrue(Mirror.me(Character.class).canCastToDirectly(Character.class));
		assertTrue(Mirror.me(char.class).canCastToDirectly(Character.class));
	}

	public void testGetWrpperClass() {
		assertEquals(Boolean.class, Mirror.me(Boolean.class).getWrpperClass());
		assertEquals(Boolean.class, Mirror.me(boolean.class).getWrpperClass());
		assertEquals(Integer.class, Mirror.me(Integer.class).getWrpperClass());
		assertEquals(Integer.class, Mirror.me(int.class).getWrpperClass());
		assertEquals(Float.class, Mirror.me(Float.class).getWrpperClass());
		assertEquals(Float.class, Mirror.me(float.class).getWrpperClass());
		assertEquals(Long.class, Mirror.me(Long.class).getWrpperClass());
		assertEquals(Long.class, Mirror.me(long.class).getWrpperClass());
		assertEquals(Double.class, Mirror.me(Double.class).getWrpperClass());
		assertEquals(Double.class, Mirror.me(double.class).getWrpperClass());
		assertEquals(Byte.class, Mirror.me(Byte.class).getWrpperClass());
		assertEquals(Byte.class, Mirror.me(byte.class).getWrpperClass());
		assertEquals(Short.class, Mirror.me(Short.class).getWrpperClass());
		assertEquals(Short.class, Mirror.me(short.class).getWrpperClass());
		assertEquals(Character.class, Mirror.me(Character.class).getWrpperClass());
		assertEquals(Character.class, Mirror.me(char.class).getWrpperClass());
	}

	public void testExtractBoolean() {
		assertEquals(boolean.class, Mirror.me(boolean.class).extractTypes()[0]);
		assertEquals(Boolean.class, Mirror.me(boolean.class).extractTypes()[1]);
	}

	public void testExtractEnum() {
		assertEquals(Nutz.class, Mirror.me(Nutz.Dao.getClass()).extractTypes()[0]);
		assertEquals(Enum.class, Mirror.me(Nutz.Dao.getClass()).extractTypes()[1]);
	}

	public void testExtractChar() {
		Class<?>[] types = Mirror.me(char.class).extractTypes();
		assertEquals(3, types.length);
		assertEquals(char.class, types[0]);
		assertEquals(Character.class, types[1]);
	}

	public static class F {
		String id;
	}

	public static class FF {

		public FF(String myId) {
			fid = myId;
		}

		public FF(F f, String myId) {
			fid = f.id + myId;
		}

		String fid;
	}

	public void testBorn_innerClassNested() {
		F f = new F();
		f.id = "haha";
		FF ff = Mirror.me(FF.class).born(f, "!!!");
		assertEquals("haha!!!", ff.fid);
	}

	public void testBorn_innerClassDefaultNested() {
		FF ff = Mirror.me(FF.class).born("!!!");
		assertEquals("!!!", ff.fid);
	}

	public static class DS {
		public DS(int id, String... values) {
			this.id = id;
			this.values = values;
		}

		private int id;
		private String[] values;
	}

	public void testBornByStaticDynamiceArgs() {
		DS ds = Mirror.me(DS.class).born(23, new String[] { "TT", "FF" });
		assertEquals(23, ds.id);
		assertEquals("FF", ds.values[1]);
	}

	public void testBornByStaticNullDynamiceArgs() {
		DS ds = Mirror.me(DS.class).born(23);
		assertEquals(23, ds.id);
		assertEquals(0, ds.values.length);
	}

	public static class DD {
		public DD(int id, String... values) {
			this.id = id;
			this.values = values;
		}

		private int id;
		private String[] values;
	}

	public void testBornByInnerDynamiceArgs() {
		DD ds = Mirror.me(DD.class).born(23, new String[] { "TT", "FF" });
		assertEquals(23, ds.id);
		assertEquals("FF", ds.values[1]);
	}

	public void testBornByInnerNullDynamiceArgs() {
		DD ds = Mirror.me(DD.class).born(23);
		assertEquals(23, ds.id);
		assertEquals(0, ds.values.length);
	}

	public void testBornByInnerOuterDynamiceArgs() {
		DD ds = Mirror.me(DD.class).born(23);
		assertEquals(23, ds.id);
		assertEquals(0, ds.values.length);
	}

	public void testBornByParent() {
		NullPointerException e = new NullPointerException();
		RuntimeException e2 = Mirror.me(RuntimeException.class).born(e);
		assertTrue(e2.getCause() == e);
	}

	public void testBornByStatic() {
		Calendar c = Mirror.me(Calendar.class).born();
		assertNotNull(c);
		Integer ii = Mirror.me(Integer.class).born(34);
		assertTrue(34 == ii);
	}

	public static class DDD {
		public String[] args;
		public String[] x_args;

		public DDD(String... args) {
			this.args = args;
		}

		public void x(String... args) {
			this.x_args = args;
		}
	}

	public void testBornByDynamicArgs() throws Exception {
		DDD d = Mirror.me(DDD.class).born((Object) Lang.array("abc", "bcd"));
		assertEquals(2, d.args.length);
		assertEquals("abc", d.args[0]);
		assertEquals("bcd", d.args[1]);
	}

	public void testInvokeByDynamicArgs() throws Exception {
		DDD d = Mirror.me(DDD.class).born((Object) Lang.array("abc", "bcd"));
		Mirror.me(DDD.class).invoke(d, "x", Lang.array("F", "Z"));
		assertEquals(2, d.x_args.length);
		assertEquals("F", d.x_args[0]);
		assertEquals("Z", d.x_args[1]);
	}

	public void testBornByDynamicArgsNull() throws Exception {
		DDD d = Mirror.me(DDD.class).born();
		assertEquals(0, d.args.length);
	}

	public void testBornByDynamicArgsObjectArray() throws Exception {
		Object[] args = new Object[2];
		args[0] = "A";
		args[1] = "B";
		DDD d = Mirror.me(DDD.class).born(args);
		assertEquals(2, d.args.length);
		assertEquals("A", d.args[0]);
		assertEquals("B", d.args[1]);
		d = Mirror.me(DDD.class).born("A", "B");
		assertEquals(2, d.args.length);
		assertEquals("A", d.args[0]);
		assertEquals("B", d.args[1]);
	}

	public static int testStaticMethod(Long l) {
		return l.intValue() * 10;
	}

	public void testInvokeStatic() {
		int re = (Integer) Mirror.me(this.getClass()).invoke(null, "testStaticMethod", 45L);
		assertEquals(450, re);
	}

	public boolean testMethod(String s) {
		return Boolean.valueOf(s);
	}

	public void testInvoke() {
		boolean re = (Boolean) Mirror.me(this.getClass()).invoke(this, "testMethod", "true");
		assertTrue(re);
	}

	public static int invokeByInt(int abc) {
		return abc * 10;
	}

	public void testInvokeByWrapper() {
		int re = (Integer) Mirror.me(this.getClass()).invoke(this, "invokeByInt", 23);
		assertEquals(230, re);
	}

	public static class SV {
		private int id;
		private char cc;
	}

	public void testSetValue() {
		SV sv = new SV();
		Mirror.me(SV.class).setValue(sv, "id", 200);
		Mirror.me(SV.class).setValue(sv, "cc", 'T');
		assertEquals(200, sv.id);
		assertEquals('T', sv.cc);
		Mirror.me(SV.class).setValue(sv, "id", null);
		Mirror.me(SV.class).setValue(sv, "cc", null);
		assertEquals(0, sv.id);
		assertEquals(0, sv.cc);
	}

}
