package com.example.geth.service.blockchain

import com.example.geth.Contracts_Dragon721_sol_Dragon721
import io.reactivex.Flowable
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.BatchRequest
import org.web3j.protocol.core.DefaultBlockParameter
import org.web3j.protocol.core.Request
import org.web3j.protocol.core.methods.request.EthFilter
import org.web3j.protocol.core.methods.request.ShhFilter
import org.web3j.protocol.core.methods.request.ShhPost
import org.web3j.protocol.core.methods.request.Transaction
import org.web3j.protocol.core.methods.response.*
import org.web3j.protocol.core.methods.response.admin.AdminDataDir
import org.web3j.protocol.core.methods.response.admin.AdminNodeInfo
import org.web3j.protocol.core.methods.response.admin.AdminPeers
import org.web3j.protocol.websocket.events.LogNotification
import org.web3j.protocol.websocket.events.NewHeadsNotification
import java.math.BigInteger

class InspectionModeDragon721Service : Dragon721Service {
    private val web3 = object : Web3j {
        override fun web3ClientVersion(): Request<*, Web3ClientVersion> {
            TODO("Not yet implemented")
        }

        override fun web3Sha3(data: String?): Request<*, Web3Sha3> {
            TODO("Not yet implemented")
        }

        override fun netVersion(): Request<*, NetVersion> {
            TODO("Not yet implemented")
        }

        override fun netListening(): Request<*, NetListening> {
            TODO("Not yet implemented")
        }

        override fun netPeerCount(): Request<*, NetPeerCount> {
            TODO("Not yet implemented")
        }

        override fun adminNodeInfo(): Request<*, AdminNodeInfo> {
            TODO("Not yet implemented")
        }

        override fun adminPeers(): Request<*, AdminPeers> {
            TODO("Not yet implemented")
        }

        override fun adminAddPeer(url: String?): Request<*, BooleanResponse> {
            TODO("Not yet implemented")
        }

        override fun adminRemovePeer(url: String?): Request<*, BooleanResponse> {
            TODO("Not yet implemented")
        }

        override fun adminDataDir(): Request<*, AdminDataDir> {
            TODO("Not yet implemented")
        }

        override fun ethProtocolVersion(): Request<*, EthProtocolVersion> {
            TODO("Not yet implemented")
        }

        override fun ethChainId(): Request<*, EthChainId> {
            TODO("Not yet implemented")
        }

        override fun ethCoinbase(): Request<*, EthCoinbase> {
            TODO("Not yet implemented")
        }

        override fun ethSyncing(): Request<*, EthSyncing> {
            TODO("Not yet implemented")
        }

        override fun ethMining(): Request<*, EthMining> {
            TODO("Not yet implemented")
        }

        override fun ethHashrate(): Request<*, EthHashrate> {
            TODO("Not yet implemented")
        }

        override fun ethGasPrice(): Request<*, EthGasPrice> {
            TODO("Not yet implemented")
        }

        override fun ethAccounts(): Request<*, EthAccounts> {
            TODO("Not yet implemented")
        }

        override fun ethBlockNumber(): Request<*, EthBlockNumber> {
            TODO("Not yet implemented")
        }

        override fun ethGetBalance(address: String?, defaultBlockParameter: DefaultBlockParameter?): Request<*, EthGetBalance> {
            TODO("Not yet implemented")
        }

        override fun ethGetStorageAt(address: String?, position: BigInteger?, defaultBlockParameter: DefaultBlockParameter?): Request<*, EthGetStorageAt> {
            TODO("Not yet implemented")
        }

        override fun ethGetTransactionCount(address: String?, defaultBlockParameter: DefaultBlockParameter?): Request<*, EthGetTransactionCount> {
            TODO("Not yet implemented")
        }

        override fun ethGetBlockTransactionCountByHash(blockHash: String?): Request<*, EthGetBlockTransactionCountByHash> {
            TODO("Not yet implemented")
        }

        override fun ethGetBlockTransactionCountByNumber(defaultBlockParameter: DefaultBlockParameter?): Request<*, EthGetBlockTransactionCountByNumber> {
            TODO("Not yet implemented")
        }

        override fun ethGetUncleCountByBlockHash(blockHash: String?): Request<*, EthGetUncleCountByBlockHash> {
            TODO("Not yet implemented")
        }

        override fun ethGetUncleCountByBlockNumber(defaultBlockParameter: DefaultBlockParameter?): Request<*, EthGetUncleCountByBlockNumber> {
            TODO("Not yet implemented")
        }

        override fun ethGetCode(address: String?, defaultBlockParameter: DefaultBlockParameter?): Request<*, EthGetCode> {
            TODO("Not yet implemented")
        }

        override fun ethSign(address: String?, sha3HashOfDataToSign: String?): Request<*, EthSign> {
            TODO("Not yet implemented")
        }

        override fun ethSendTransaction(transaction: Transaction?): Request<*, EthSendTransaction> {
            TODO("Not yet implemented")
        }

        override fun ethSendRawTransaction(signedTransactionData: String?): Request<*, EthSendTransaction> {
            TODO("Not yet implemented")
        }

        override fun ethCall(transaction: Transaction?, defaultBlockParameter: DefaultBlockParameter?): Request<*, EthCall> {
            TODO("Not yet implemented")
        }

        override fun ethEstimateGas(transaction: Transaction?): Request<*, EthEstimateGas> {
            TODO("Not yet implemented")
        }

        override fun ethGetBlockByHash(blockHash: String?, returnFullTransactionObjects: Boolean): Request<*, EthBlock> {
            TODO("Not yet implemented")
        }

        override fun ethGetBlockByNumber(defaultBlockParameter: DefaultBlockParameter?, returnFullTransactionObjects: Boolean): Request<*, EthBlock> {
            TODO("Not yet implemented")
        }

        override fun ethGetTransactionByHash(transactionHash: String?): Request<*, EthTransaction> {
            TODO("Not yet implemented")
        }

        override fun ethGetTransactionByBlockHashAndIndex(blockHash: String?, transactionIndex: BigInteger?): Request<*, EthTransaction> {
            TODO("Not yet implemented")
        }

        override fun ethGetTransactionByBlockNumberAndIndex(defaultBlockParameter: DefaultBlockParameter?, transactionIndex: BigInteger?): Request<*, EthTransaction> {
            TODO("Not yet implemented")
        }

        override fun ethGetTransactionReceipt(transactionHash: String?): Request<*, EthGetTransactionReceipt> {
            TODO("Not yet implemented")
        }

        override fun ethGetUncleByBlockHashAndIndex(blockHash: String?, transactionIndex: BigInteger?): Request<*, EthBlock> {
            TODO("Not yet implemented")
        }

        override fun ethGetUncleByBlockNumberAndIndex(defaultBlockParameter: DefaultBlockParameter?, transactionIndex: BigInteger?): Request<*, EthBlock> {
            TODO("Not yet implemented")
        }

        override fun ethGetCompilers(): Request<*, EthGetCompilers> {
            TODO("Not yet implemented")
        }

        override fun ethCompileLLL(sourceCode: String?): Request<*, EthCompileLLL> {
            TODO("Not yet implemented")
        }

        override fun ethCompileSolidity(sourceCode: String?): Request<*, EthCompileSolidity> {
            TODO("Not yet implemented")
        }

        override fun ethCompileSerpent(sourceCode: String?): Request<*, EthCompileSerpent> {
            TODO("Not yet implemented")
        }

        override fun ethNewFilter(ethFilter: EthFilter?): Request<*, org.web3j.protocol.core.methods.response.EthFilter> {
            TODO("Not yet implemented")
        }

        override fun ethNewBlockFilter(): Request<*, org.web3j.protocol.core.methods.response.EthFilter> {
            TODO("Not yet implemented")
        }

        override fun ethNewPendingTransactionFilter(): Request<*, org.web3j.protocol.core.methods.response.EthFilter> {
            TODO("Not yet implemented")
        }

        override fun ethUninstallFilter(filterId: BigInteger?): Request<*, EthUninstallFilter> {
            TODO("Not yet implemented")
        }

        override fun ethGetFilterChanges(filterId: BigInteger?): Request<*, EthLog> {
            TODO("Not yet implemented")
        }

        override fun ethGetFilterLogs(filterId: BigInteger?): Request<*, EthLog> {
            TODO("Not yet implemented")
        }

        override fun ethGetLogs(ethFilter: EthFilter?): Request<*, EthLog> {
            TODO("Not yet implemented")
        }

        override fun ethGetWork(): Request<*, EthGetWork> {
            TODO("Not yet implemented")
        }

        override fun ethSubmitWork(nonce: String?, headerPowHash: String?, mixDigest: String?): Request<*, EthSubmitWork> {
            TODO("Not yet implemented")
        }

        override fun ethSubmitHashrate(hashrate: String?, clientId: String?): Request<*, EthSubmitHashrate> {
            TODO("Not yet implemented")
        }

        override fun dbPutString(databaseName: String?, keyName: String?, stringToStore: String?): Request<*, DbPutString> {
            TODO("Not yet implemented")
        }

        override fun dbGetString(databaseName: String?, keyName: String?): Request<*, DbGetString> {
            TODO("Not yet implemented")
        }

        override fun dbPutHex(databaseName: String?, keyName: String?, dataToStore: String?): Request<*, DbPutHex> {
            TODO("Not yet implemented")
        }

        override fun dbGetHex(databaseName: String?, keyName: String?): Request<*, DbGetHex> {
            TODO("Not yet implemented")
        }

        override fun shhPost(shhPost: ShhPost?): Request<*, org.web3j.protocol.core.methods.response.ShhPost> {
            TODO("Not yet implemented")
        }

        override fun shhVersion(): Request<*, ShhVersion> {
            TODO("Not yet implemented")
        }

        override fun shhNewIdentity(): Request<*, ShhNewIdentity> {
            TODO("Not yet implemented")
        }

        override fun shhHasIdentity(identityAddress: String?): Request<*, ShhHasIdentity> {
            TODO("Not yet implemented")
        }

        override fun shhNewGroup(): Request<*, ShhNewGroup> {
            TODO("Not yet implemented")
        }

        override fun shhAddToGroup(identityAddress: String?): Request<*, ShhAddToGroup> {
            TODO("Not yet implemented")
        }

        override fun shhNewFilter(shhFilter: ShhFilter?): Request<*, ShhNewFilter> {
            TODO("Not yet implemented")
        }

        override fun shhUninstallFilter(filterId: BigInteger?): Request<*, ShhUninstallFilter> {
            TODO("Not yet implemented")
        }

        override fun shhGetFilterChanges(filterId: BigInteger?): Request<*, ShhMessages> {
            TODO("Not yet implemented")
        }

        override fun shhGetMessages(filterId: BigInteger?): Request<*, ShhMessages> {
            TODO("Not yet implemented")
        }

        override fun txPoolStatus(): Request<*, TxPoolStatus> {
            TODO("Not yet implemented")
        }

        override fun ethLogFlowable(ethFilter: EthFilter?): Flowable<Log> {
            TODO("Not yet implemented")
        }

        override fun ethBlockHashFlowable(): Flowable<String> {
            TODO("Not yet implemented")
        }

        override fun ethPendingTransactionHashFlowable(): Flowable<String> {
            TODO("Not yet implemented")
        }

        override fun transactionFlowable(): Flowable<org.web3j.protocol.core.methods.response.Transaction> {
            TODO("Not yet implemented")
        }

        override fun pendingTransactionFlowable(): Flowable<org.web3j.protocol.core.methods.response.Transaction> {
            TODO("Not yet implemented")
        }

        override fun blockFlowable(fullTransactionObjects: Boolean): Flowable<EthBlock> {
            TODO("Not yet implemented")
        }

        override fun replayPastBlocksFlowable(startBlock: DefaultBlockParameter?, endBlock: DefaultBlockParameter?, fullTransactionObjects: Boolean): Flowable<EthBlock> {
            TODO("Not yet implemented")
        }

        override fun replayPastBlocksFlowable(startBlock: DefaultBlockParameter?, endBlock: DefaultBlockParameter?, fullTransactionObjects: Boolean, ascending: Boolean): Flowable<EthBlock> {
            TODO("Not yet implemented")
        }

        override fun replayPastBlocksFlowable(startBlock: DefaultBlockParameter?, fullTransactionObjects: Boolean, onCompleteFlowable: Flowable<EthBlock>?): Flowable<EthBlock> {
            TODO("Not yet implemented")
        }

        override fun replayPastBlocksFlowable(startBlock: DefaultBlockParameter?, fullTransactionObjects: Boolean): Flowable<EthBlock> {
            TODO("Not yet implemented")
        }

        override fun replayPastTransactionsFlowable(startBlock: DefaultBlockParameter?, endBlock: DefaultBlockParameter?): Flowable<org.web3j.protocol.core.methods.response.Transaction> {
            TODO("Not yet implemented")
        }

        override fun replayPastTransactionsFlowable(startBlock: DefaultBlockParameter?): Flowable<org.web3j.protocol.core.methods.response.Transaction> {
            TODO("Not yet implemented")
        }

        override fun replayPastAndFutureBlocksFlowable(startBlock: DefaultBlockParameter?, fullTransactionObjects: Boolean): Flowable<EthBlock> {
            throw NoSuchMethodException()
        }

        override fun replayPastAndFutureTransactionsFlowable(startBlock: DefaultBlockParameter?): Flowable<org.web3j.protocol.core.methods.response.Transaction> {
            throw NoSuchMethodException()
        }

        override fun newHeadsNotifications(): Flowable<NewHeadsNotification> {
            throw NoSuchMethodException()
        }

        override fun logsNotifications(addresses: MutableList<String>?, topics: MutableList<String>?): Flowable<LogNotification> {
            throw NoSuchMethodException()
        }

        override fun newBatch(): BatchRequest {
            throw NoSuchMethodException()
        }

        override fun shutdown() {
            throw NoSuchMethodException()
        }
    }

    override fun getVersion(): String = "Dragon721 v1.0"
    override fun getAccounts(): List<String> = listOf("john", "yong")
    override fun getBalance(address: String): String = "123.456"
    override fun loadContract(contractAddress: String, privateKey: String) {}
    override fun getSymbol(): String = "Dragon721"
    override fun getTokenUri(tokenId: Long): String = "https://ipfs.io/ipfs/bafyreieptcrsawxiyqw75yc5mmgewmosneyohacc6mfzblivj3yiqrz3zq"
    override fun getAllArtworks(): List<Contracts_Dragon721_sol_Dragon721.Artwork> = emptyList()
    override suspend fun downloadToken(tokenId: BigInteger, callback: (String) -> Unit) {}
}