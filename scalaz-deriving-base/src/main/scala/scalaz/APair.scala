// Copyright: 2017 - 2020 Sam Halliday
// License: https://opensource.org/licenses/BSD-3-Clause

package scalaz

import scala.inline
import scala.Some

/**
 * Type-aligned pair. Isomorphic to
 *
 * {{{
 * (F[A], G[A]) forSome { type A }
 * }}}
 *
 * but more robust with respect to type inference.
 *
 * Notation is a blend of conjunction `/\` and natural transformation `~>`.
 */
sealed abstract class /~\[A[_], B[_]] {
  type T
  def a: A[T]
  def b: B[T]
}
object /~\                            {
  type APair[A[_], B[_]]  = A /~\ B
  type Aux[A[_], B[_], Z] = /~\[A, B] { type T = Z }

  @inline final def unapply[A[_], B[_]](p: A /~\ B): Some[(A[p.T], B[p.T])] =
    Some((p.a, p.b))

  @inline final def apply[A[_], B[_], Z](az: =>A[Z], bz: =>B[Z]): A /~\ B =
    new /~\[A, B] {
      type T = Z
      def a: A[Z] = az
      def b: B[Z] = bz
    }
}
