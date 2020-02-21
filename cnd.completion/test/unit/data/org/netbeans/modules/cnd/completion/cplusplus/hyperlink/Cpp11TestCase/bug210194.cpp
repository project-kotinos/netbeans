/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

template<typename Signature>
struct bug210194_function_traits;

template<typename R, typename... ArgTypes>
struct bug210194_function_traits<R(ArgTypes...)> {
  typedef R result_type;
};

template<typename T, typename U>
struct bug210194_same_type {
  static const bool value = false;
};

template<typename T>
struct bug210194_same_type<T, T> {
  static const bool value = true;
};

int bug210194_a0[bug210194_same_type<bug210194_function_traits<int()>::result_type, int>::value? 1 : -1];
int bug210194_a1[bug210194_same_type<bug210194_function_traits<int(float)>::result_type, int>::value? 1 : -1];
int bug210194_a2[bug210194_same_type<bug210194_function_traits<int(double, char)>::result_type, int>::value? 1 : -1];
