/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package link.thingscloud.remoting.impl.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import link.thingscloud.remoting.api.buffer.RemotingBuffer;
import link.thingscloud.remoting.api.command.RemotingCommand;
import link.thingscloud.remoting.api.exception.RemotingCodecException;
import link.thingscloud.remoting.impl.buffer.NettyRemotingBuffer;
import link.thingscloud.remoting.impl.command.CodecHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Decoder extends ByteToMessageDecoder {
    private static final Logger LOG = LoggerFactory.getLogger(Decoder.class);

    public Decoder() {
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (!in.isReadable()) {
            return;
        }

        NettyRemotingBuffer wrapper = new NettyRemotingBuffer(in);

        Object msg = this.decode(ctx, wrapper);
        if (msg != null) {
            out.add(msg);
        }
    }

    private Object decode(final ChannelHandlerContext ctx, RemotingBuffer wrapper) throws Exception {
        int originReaderIndex = wrapper.readerIndex();

        byte magic = wrapper.readByte();
        try {
            if (magic != CodecHelper.PROTOCOL_MAGIC) {
                throw new RemotingCodecException(String.format("MagicCode %d is wrong, expect %d", magic, CodecHelper.PROTOCOL_MAGIC));
            }
            return decode(wrapper, originReaderIndex);
        } catch (final RemotingCodecException e) {
            LOG.warn("Decode error {}, close the channel {}", e.getMessage(), ctx.channel());
            ctx.channel().close().addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    LOG.warn("Close channel {} because of error {},result is {}", ctx.channel(), e, future.isSuccess());
                }
            });
        }
        return null;
    }

    public RemotingCommand decode(final RemotingBuffer wrapper, final int originReaderIndex) {
        // Full message isn't available yet, return nothing for now
        if (wrapper.readableBytes() < CodecHelper.MIN_PROTOCOL_LEN - 1 /*MagicCode*/) {
            wrapper.setReaderIndex(originReaderIndex);
            return null;
        }

        int totalLength = wrapper.readInt();

        if (totalLength <= 0) {
            throw new RemotingCodecException("Illegal total length " + totalLength);
        }

        if (totalLength > CodecHelper.PACKET_MAX_LEN) {
            throw new RemotingCodecException(String.format("Total length %d is more than limit %d", totalLength, CodecHelper.PACKET_MAX_LEN));
        }

        if (wrapper.readableBytes() < totalLength - 1 /*MagicCode*/ - 4 /*TotalLen*/) {
            wrapper.setReaderIndex(originReaderIndex);
            return null;
        }
        return CodecHelper.decode(wrapper);
    }
}
