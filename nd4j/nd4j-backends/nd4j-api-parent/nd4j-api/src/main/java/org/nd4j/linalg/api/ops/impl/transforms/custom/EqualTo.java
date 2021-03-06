/*******************************************************************************
 * Copyright (c) 2015-2018 Skymind, Inc.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Apache License, Version 2.0 which is available at
 * https://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 ******************************************************************************/

package org.nd4j.linalg.api.ops.impl.transforms.custom;

import org.nd4j.autodiff.samediff.SDVariable;
import org.nd4j.autodiff.samediff.SameDiff;
import org.nd4j.linalg.api.buffer.DataType;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.api.ops.impl.transforms.BaseDynamicTransformOp;
import org.nd4j.linalg.api.shape.LongShapeDescriptor;
import org.nd4j.linalg.factory.Nd4j;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Bit mask over the ndarrays as to whether
 * the components are equal or not
 *
 * @author Adam Gibson
 */
public class EqualTo extends BaseDynamicTransformOp {
    public EqualTo() {}

    public EqualTo( SameDiff sameDiff, SDVariable[] args, boolean inPlace) {
        super(sameDiff, args, inPlace);
    }

    public EqualTo( INDArray[] inputs, INDArray[] outputs) {
        super(inputs, outputs);
    }


    @Override
    public String opName() {
        return "equals";
    }

    @Override
    public String onnxName() {
        return "Equal";
    }

    @Override
    public String tensorflowName() {
        return "Equal";
    }

    @Override
    public List<SDVariable> doDiff(List<SDVariable> f1) {
        //Equals op: 2 inputs, not continuously differentiable but 0s almost everywhere
        return Arrays.asList(sameDiff.zerosLike(args()[0]), sameDiff.zerosLike(args()[1]));
    }

    @Override
    public List<LongShapeDescriptor> calculateOutputShape() {
        if (args() == null)
            return Collections.emptyList();

        if (inputArguments.size() == 0)
            return Collections.emptyList();


        return Nd4j.getExecutioner().calculateOutputShape(this);
    }
}
